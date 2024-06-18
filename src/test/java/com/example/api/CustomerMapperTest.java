package com.example.api;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest //MapperクラスをDIしてあげたい
@Transactional //テストの前後でテーブルの中身が変更しないようにする
@Import(DbConfig.class)
public class CustomerMapperTest {

	@Autowired
	private CustomerMapper mapper; //テスト対象

	@Autowired
	private DataSource ds; //データソース設定情報を保持しているBean DataSource をDI

	private IDatabaseConnection dbconn; //データベースとのコネクション

	private IDataSet inputCsvDataSet; //データセットを持たせる

	/**
	 * Clean and insert test data before each test method.
	 *
	 * @throws Exception SQLException thrown when connecting to database
	 */
	@BeforeEach
	public void setup() throws Exception { //setup メソッド内では，各テスト前にデータベースの初期化処理を行っています
		this.dbconn = new DatabaseConnection(this.ds.getConnection());
		this.inputCsvDataSet = new CsvDataSet(new File("src/test/resources/com/numacci/api/repository"));
		DatabaseOperation.CLEAN_INSERT.execute(dbconn, inputCsvDataSet);
	}//データソースからデータベース接続を取得し，src/test/resources/com/example/api 配下に存在するCSVファイルをデータベースに CLEAN & INSERT (=DeleteAll+Insert) しています

	/**
	 * Close database connection after each test method.
	 *
	 * @throws Exception SQLException thrown when closing the connection
	 */
	@AfterEach
	public void teardown() throws Exception {
		this.dbconn.close();//各テスト後にデータベース接続をクローズ
	}

	@DisplayName("INSERT TEST: Check if the data is inserted as expected.")
	@Test
	public void testInsert() {//Insertしたいオブジェクトを作成し，mapper.insert メソッドをコールしています．メソッドをコールした返り値が1である＝新規挿入件数が1件であるかどうかを最後に確認していて，1ならばテスト成功，それ以外ならばテスト失敗 (=Insertされなかった，等) となります
		Customer customer = new Customer();
		customer.setId("100");
		customer.setUsername("user100");
		customer.setEmail("test.user.100@example.com");
		customer.setPhoneNumber("01234567890");
		customer.setPostCode("1234567");

		assertEquals(1, mapper.insert(customer));
	}
}