package br.com.fisioQCM;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.fisioQCM.QuestionarioQCM;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GerenciadorBancoDadosQCM extends SQLiteOpenHelper{
	
		private static final String NOME_BANCO = "questionarioQCM.db"; //atributos de identificação do banco de dados
		private static final int VERSAO_BANCO = 1;
		private static final String TABELA_QUESTIONARIOS = "questionarios";
		
		
		public GerenciadorBancoDadosQCM (Context context) // contrutor que passa os parametros do banco que vai ser criado para a classe pai o criar.
		{
			super(context, NOME_BANCO, null, VERSAO_BANCO);
		}
		
		public void onCreate(SQLiteDatabase db) // método de criação do banco de dados e suas tabelas.
		{
			db.execSQL("CREATE TABLE "+ TABELA_QUESTIONARIOS +" (id INTEGER PRIMARY KEY AUTOINCREMENT," 
						+ " quest1_sabeExame INTEGER NOT NULL, quest1_quais TEXT, quest2_ouviuFalarAutoexame INTEGER NOT NULL,"
						+ " quest3_SabeRealizarAutoexame INTEGER NOT NULL, quest4_SabeOQueAvaliar INTEGER NOT NULL, quest4_oque TEXT,"
						+ " quest5_qualquerPeriodo INTEGER NOT NULL, quest5_periodoMenstrual INTEGER NOT NULL, quest5_antesPeriodoMenstrual INTEGER NOT NULL,"
						+ " quest5_aposPeriodoMenstrual INTEGER NOT NULL, quest6_realizaAutoexame INTEGER NOT NULL, quest7_medo INTEGER NOT NULL,"
						+ " quest7_esquecimento INTEGER NOT NULL, quest7_vergonha INTEGER NOT NULL, quest7_outros INTEGER NOT NULL, quest7_etOutros TEXT,"
						+ " quest8_homemTemCancerMama INTEGER NOT NULL);"); // string em sql que diz como vai ser criado o banco de dados.
		}
		
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) // método para atualizar o banco de dados para uma nova versão caso faça alterações na aplicação.
		{
			
		}
		
		public void inserirQuestionario(QuestionarioQCM questionario) {   // método para inserir uma pessoa no banco de dados;
		    
			ContentValues valores = new ContentValues(); 	 
		    valores.put("quest1_sabeExame", questionario.quest1_sabeExame);            //insere na coluna nome o valor de p_pessoa.nome().
		    valores.put("quest1_quais", questionario.quest1_qual);    
		    valores.put("quest2_ouviuFalarAutoexame", questionario.quest2_ouviuFalarAutoexame);         
		    valores.put("quest3_SabeRealizarAutoexame", questionario.quest3_SabeRealizarAutoexame);
		    valores.put("quest4_SabeOQueAvaliar", questionario.quest4_SabeOQueAvaliar);
		    valores.put("quest4_oque", questionario.quest4_oque);
		    valores.put("quest5_qualquerPeriodo", questionario.quest5_qualquerPeriodo);		    
		    valores.put("quest5_periodoMenstrual", questionario.quest5_periodoMenstrual);    
		    valores.put("quest5_antesPeriodoMenstrual", questionario.quest5_antesPeriodoMenstrual);         
		    valores.put("quest5_aposPeriodoMenstrual", questionario.quest5_aposPeriodoMenstrual);
		    valores.put("quest6_realizaAutoexame", questionario.quest6_realizaAutoexame);
		    valores.put("quest7_medo", questionario.quest7_medo);
		    valores.put("quest7_esquecimento", questionario.quest7_esquecimento);
		    valores.put("quest7_vergonha", questionario.quest7_vergonha);    
		    valores.put("quest7_outros", questionario.quest7_outros);
		    valores.put("quest7_etOutros", questionario.quest7_etOutros);         
		    valores.put("quest8_homemTemCancerMama", questionario.quest8_homemTemCancerMama);
		    
		    getWritableDatabase().insert(TABELA_QUESTIONARIOS, null, valores); // pega uma versão para escrita do banco e insere os valores de pessoa na tabela pessoas.
		}
		
		
		public int getQuestionariosCount() {      // método para contar o número de pessoas cadastradas na tabela pessoas.
	        String countQuery = "SELECT  * FROM " + TABELA_QUESTIONARIOS;  //string em sql para a busca.
	        SQLiteDatabase db = this.getReadableDatabase(); // pega uma versão de leitura do banco de dados.
	        Cursor cursor = db.rawQuery(countQuery, null); // armazena em cursor o resultado da busca no banco.	 
	        return cursor.getCount(); // retorna a quantidade de ocorrencias de dados na tabela pessoas.
	    }
		
		public List<QuestionarioQCM> getAllQuestionarios() {
		    List<QuestionarioQCM> listaQuestionarios = new ArrayList<QuestionarioQCM>();
		    // Select All Query
		    String selectQuery = "SELECT * FROM " + TABELA_QUESTIONARIOS;
		 
		    SQLiteDatabase db = this.getWritableDatabase();
		    Cursor cursor = db.rawQuery(selectQuery, null);
		 
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
		        do {
		            QuestionarioQCM questionario = new QuestionarioQCM();		            
		            questionario.quest1_sabeExame= Integer.parseInt(cursor.getString(0));            //insere na coluna nome o valor de p_pessoa.nome().
				    questionario.quest1_qual = cursor.getString(1);    
				    questionario.quest2_ouviuFalarAutoexame = Integer.parseInt(cursor.getString(2));         
				    questionario.quest3_SabeRealizarAutoexame = Integer.parseInt(cursor.getString(3));
				    questionario.quest4_SabeOQueAvaliar = Integer.parseInt(cursor.getString(4));   
				    questionario.quest4_oque = cursor.getString(5);
				    questionario.quest5_qualquerPeriodo = Integer.parseInt(cursor.getString(6));		    
				    questionario.quest5_periodoMenstrual = Integer.parseInt(cursor.getString(7));    
				    questionario.quest5_antesPeriodoMenstrual = Integer.parseInt(cursor.getString(8));         
				    questionario.quest5_aposPeriodoMenstrual = Integer.parseInt(cursor.getString(9));
				    questionario.quest6_realizaAutoexame = Integer.parseInt(cursor.getString(10));
				    questionario.quest7_medo = Integer.parseInt(cursor.getString(11));
				    questionario.quest7_esquecimento = Integer.parseInt(cursor.getString(12));
				    questionario.quest7_vergonha = Integer.parseInt(cursor.getString(13));    
				    questionario.quest7_outros = Integer.parseInt(cursor.getString(14));
				    questionario.quest7_etOutros = cursor.getString(15);         
				    questionario.quest8_homemTemCancerMama = Integer.parseInt(cursor.getString(16));         
		            
		            // Adding questionario to list
		            listaQuestionarios.add(questionario);
		        } while (cursor.moveToNext());
		    }
		   return listaQuestionarios;
		}
		
		public Vector<String> SalvaBDString()
		{
			
			Vector<String> dados = new Vector<String>();
					dados.add("quest1_sabeExame; quest1_quais ; quest2_ouviuFalarAutoexame ; quest3_SabeRealizarAutoexame;"
					+ "quest4_SabeOQueAvaliar; quest4_oque ; quest5_qualquerPeriodo ; quest5_periodoMenstrual ; quest5_antesPeriodoMenstrual;"
					+ "quest5_aposPeriodoMenstrual; quest6_realizaAutoexame ; quest7_medo ; quest7_esquecimento ; quest7_vergonha ; quest7_outros"
					+ "quest7_etOutros ; quest8_homemTemCancerMama"); 
			
			 String selectQuery = "SELECT * FROM " + TABELA_QUESTIONARIOS;
			 
			    SQLiteDatabase db = this.getWritableDatabase();
			    Cursor cursor = db.rawQuery(selectQuery, null);
			    
			    if (cursor.moveToFirst()) 
			    {
			        do {
			        	String linha = cursor.getString(1) + ";" + cursor.getString(2) + ";" + cursor.getString(3) + ";" +	cursor.getString(4) + ";" + 
			        		cursor.getString(5) + ";" + cursor.getString(6) + ";" + cursor.getString(7) + ";" +	cursor.getString(8) + ";" + 
			        		cursor.getString(9) + ";" + cursor.getString(10) + ";" + cursor.getString(11) + ";" + cursor.getString(12) + ";" + 
			        		cursor.getString(13) + ";" + cursor.getString(14) + ";" + cursor.getString(15) + ";" + cursor.getString(16);
			        	dados.add(linha);	
			        } while (cursor.moveToNext());
			        
			    }
			    return dados;
		}

}
