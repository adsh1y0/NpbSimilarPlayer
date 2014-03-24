# Jubatus Recommender Example.
-----

jubatus-exampleのRecommenderサンプルをjavaで実装してみた。  
当サンプルにはpython,rubyは用意されているがjavaが無かったので。。  
（自分以外にpython,rubyを読んでくれる人がいない。。)

できるだけrubyやpythonのコード例に合わせています。  
基のコードは以下。

<a>http://jubat.us/ja/tutorial/recommender_ruby.html</a>

# settings.
動作検証はUbuntu Server 12.04LTS（64bit）です。
環境構築は本家を参照。

<a>http://jubat.us/ja/quickstart.html#ubuntu-server-12-04-lts-64-bit</a>

## ubuntuへのgradleインストール
ビルドツールはmavenではなくgradleを利用しています。  
ppaが提供されているので以下のリポジトリをaptに設定すれば簡単にインストール可能です。

    sudo add-apt-repository ppa:cwchien/gradle
    sudo apt-get update
    sudo apt-get install gradle


# Tutorial
※ 以下はexampleのnpb_similar_playerのREADME.mdとほぼ一緒です。

## NPBの似た野手探し
2012年日本プロ野球の野手成績を学習し、似たタイプの選手を出力します。

このサンプルでは、 num_feature の plugin を利用することもできます。plugin は以下の手順でインストールします。
Jubatus の plugin ディレクトリにビルドした .soファイル をコピーしておきます。

    $ cd normalize_plugin
    $ ./waf configure
    $ ./waf build
    $ cp build/src/*.so /path/to/jubatus/lib/jubatus/plugin

## サーバーの起動
jubarecommender を起動します。

    $ jubarecommender --configpath npb_similar_player.json &

plugin を利用する場合は、 npb_similar_player.plugin.json を指定します。

## 実行（クライアント)

baseball.csvは、 プロ野球データfreak(http://baseball-data.com/) から取得した  
「規定打席の1/3以上の全野手のデータ」を打席数順にソートしたものです。

このデータを学習し、それぞれの似たタイプの選手を表示します。

当チュートリアルはgradleによる実行方法を提供しています。

    gradle run
