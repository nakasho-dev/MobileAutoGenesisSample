#!/usr/bin/env python3
"""Validate that the full mobile demo plan covers every Gherkin scenario exactly once."""

import argparse
import json
import pathlib
import re
import sys


SCENARIO_PATTERN = re.compile(r"^\s*Scenario(?: Outline)?:\s*(.+?)\s*$")


def scenario_names(feature_path: pathlib.Path) -> list[str]:
    return [
        match.group(1)
        for line in feature_path.read_text(encoding="utf-8").splitlines()
        if (match := SCENARIO_PATTERN.match(line)) is not None
    ]


def main() -> int:
    parser = argparse.ArgumentParser(
        description="Validate config/full_mobile_demo_plan.json against Gherkin features."
    )
    parser.add_argument(
        "--print",
        action="store_true",
        dest="print_plan",
        help="Print the ordered feature and scenario list after validation.",
    )
    args = parser.parse_args()

    workspace = pathlib.Path(__file__).resolve().parents[2]
    plan_path = workspace / "config" / "full_mobile_demo_plan.json"
    plan = json.loads(plan_path.read_text(encoding="utf-8"))
    failures: list[str] = []
    planned_features = [entry["feature"] for entry in plan["features"]]
    feature_directory = workspace / "behave-demo" / "features"
    actual_features = sorted(path.stem for path in feature_directory.glob("*.feature"))

    if len(planned_features) != len(set(planned_features)):
        failures.append("Demo plan contains a feature more than once.")
    if set(planned_features) != set(actual_features):
        failures.append(
            "Feature mismatch: "
            f"plan={', '.join(planned_features)}; "
            f"Gherkin={', '.join(actual_features)}"
        )

    planned_scenarios: list[str] = []
    actual_scenarios: list[str] = []
    for entry in plan["features"]:
        feature = entry["feature"]
        scenarios = entry["scenarios"]
        planned_scenarios.extend(scenarios)
        feature_path = feature_directory / f"{feature}.feature"
        if feature_path.exists():
            actual = scenario_names(feature_path)
            actual_scenarios.extend(actual)
            if scenarios != actual:
                failures.append(
                    f"Scenario order mismatch in {feature}.feature: "
                    f"plan={scenarios}; Gherkin={actual}"
                )

    if len(planned_scenarios) != len(set(planned_scenarios)):
        failures.append("Demo plan contains a scenario more than once.")
    if len(actual_scenarios) != len(set(actual_scenarios)):
        failures.append("Gherkin contains a scenario name more than once.")

    if failures:
        for failure in failures:
            print(f"FAIL: {failure}", file=sys.stderr)
        return 1

    print(
        f"PASS: full mobile demo plan covers {len(planned_features)} features "
        f"and {len(planned_scenarios)} scenarios"
    )
    if args.print_plan:
        for entry in plan["features"]:
            for scenario in entry["scenarios"]:
                print(f"{entry['feature']}: {scenario}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())