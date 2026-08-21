---
name: Generate Mobile Steps
description: AndroidまたはiOSのGherkinシナリオをローカルAppiumで実行し、AutoGenesis Behave stepを生成する
argument-hint: 'platform=android|ios scenario_name="Scenario name"'
agent: agent
---

`autoGenesis-run` skillを使用し、次の入力でシナリオを実行してBehave stepを生成してください。

- platform: `${input:platform}`
- scenario_name: `${input:scenario_name}`

ローカルAppiumのみを使用し、skillのtool順序、OS別出力先、preview/confirm条件を厳守してください。