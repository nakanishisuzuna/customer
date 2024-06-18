package com.example.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {

	/**
	 * Insert a new customer record to the database.
	 *
	 * @param customer customer object which we want to insert
	 * @return the number of inserted records
	 */
	int insert(Customer customer);

	List<Customer> selectAll();

	Customer select(String id);

	Customer update(Customer customer);

	int delete(String customerId);
}

//この insert メソッドですが，インタフェースなのでメソッドの実装はしていません．かつ，このMapperを明示的にimplementsしたクラスも作成しません．。insert の処理を実装は、XMLファイルに実行させたいSQL文を書くことによって，MyBatisがよしなに処理を実行してくれます

/*Mapper/XML
mapper namespace ではどのMapperインタフェースの実装かを宣言しています．
<mapper></mapper> 内部がこのファイルのコアです．まず <insert> ですが，MyBatisでは何の種類のSQLを実行するのかをタグで宣言してあげます．今回の処理内容は insert into なので，ここでは <insert> タグを記載します．parameterType はこの insert ステートメントに渡されるパラメータ customer の完全修飾クラス名を表しています．
<insert></insert> 内部が実行するSQL文です．インタフェースのメソッドで引数として与えた customer のフィールドには #{fieldName} でアクセスすることができます．jdbcType はデータベース側での型を表しています．*/