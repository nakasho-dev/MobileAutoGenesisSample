---
name: Full Mobile Demo
description: 全Gherkinシナリオを依存順に実装し、単体テストとローカルAppiumテストまで完了する
argument-hint: 'platform=android|ios|both'
agent: agent
---

`full-mobile-demo` skillを使用して、次の対象に対する完全デモを実行してください。

- platform: `${input:platform}`

最初にデモ計画を検証し、`login`、`list_and_detail`、`settings`の順で進めてください。各featureで実装、対象OSの単体テストとdebug build、AutoGenesisによる全scenarioのstep生成、Behave再実行を完了してから次のfeatureへ進みます。最後に対象platformのタグ付きBehave全件実行まで行ってください。

ローカルAppiumのみを使用し、生成領域の手動編集や、Gherkinの変更は行わないでください。