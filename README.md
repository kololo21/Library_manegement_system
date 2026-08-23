# Library Management System

図書館の係員が本・会員・貸出記録を管理し、貸出と返却を行うデスクトップGUIアプリケーション。
外部ライブラリを使用せず、Java SE の標準機能のみで実装。

## 技術構成
- 言語：Java SE（外部ライブラリなし）
- UI：Swing
- 永続化：Javaオブジェクトの直列化（.datファイル）
- 規模：18ファイル・約1,270行・4パッケージ

## システム構成
```
[ ui ]        Swing。MainFrameで4タブを統括
              DashboardPanel / BookPanel / MemberPanel / LoanHistoryPanel
                │
[ service ]   BookService / MemberService / LoanService / RecommondationService
                │
[ data ]      DataManager（.datファイルの読み書きを一元管理）
                │
[ model ]     Book / Member / LoanRecord / LoanStatus（Serializable）
```
依存の向きは ui → service → data → model の一方向のみ。

## 機能
- 本・会員のCRUD、検索（ID・タイトル・著者・ジャンル・氏名等）
- 貸出・返却。貸出日から14日後を返却期限として自動設定
- 期限超過をOVERDUEとして自動判定
- ダッシュボード（総冊数・会員数・貸出中件数・延滞件数・延滞者一覧）
- 貸出回数から算出する人気書籍TOP3
- 貸出中の本・借用中の会員は削除不可（整合性制約）

## 設計上のポイント
- 貸出記録は会員氏名ではなく会員IDのみを保持。会員情報の編集後も記録側との齟齬が生じない
- service層でデータ変更のたびに自動保存し、保存忘れによる不整合を防止

## 実行方法
```
javac -d out (Get-ChildItem -Path src -Recurse -Filter *.java).FullName
java -cp out Main
```

## 既知の改善余地
- ID採番がリストの要素数に基づいており、削除を伴う運用では衝突し得る
