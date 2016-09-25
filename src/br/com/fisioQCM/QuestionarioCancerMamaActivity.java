package br.com.fisioQCM;

import java.util.Vector;

import br.com.fisioQCM.GravaSD;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class QuestionarioCancerMamaActivity extends Activity {
	GerenciadorBancoDadosQCM gerenciador;
    /** Called when the activity is first created. */
    @Override
    public void onBackPressed() 
    {
    	ExibirMensagemOK("AVISO", "ESTE BOTÃO FOI CONFIGURADO PARA NÃO VOLTAR PARA A QUESTÃO ANTERIOR E NÃO ENCERRAR ESTA APLICAÇÃO!");
 	   	return;
 	}
    
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);        
        gerenciador = new GerenciadorBancoDadosQCM(this);
        chamaMenu();        
    }
          
    public void chamaMenu()

    {
    	setContentView(R.layout.menu);
    	final QuestionarioQCM questionario = new QuestionarioQCM();    	
    	questionario.resetQuestionario();
    	Button menu_btIniciar = (Button) findViewById(R.id.menu_btIniciar), menu_btSair = (Button) findViewById(R.id.menu_btSair), menu_btGerarCSV =(Button) findViewById(R.id.menu_btGerarCSV);    	
    	menu_btIniciar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				chamaQuest1(questionario);				
			}
		});
    	menu_btGerarCSV.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {	
				Vector<String> dados = gerenciador.SalvaBDString();
				
				if(GravaSD.grava("BkpBancoDadosQCM.csv", dados))
					ExibirMensagemOK("AVISO!", "DADOS EXPORTADOS COM SUCESSO PARA O DIRETÓRIO RAIZ DO CARTÃO DE MEMÓRIA");
				else
					ExibirMensagemOK("ERRO!", "O ARQUIVO NÃO PODE SER GRAVADO!");
			}
		});
    	
    	
    	menu_btSair.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				finish();				
			}
		});
    	
    	
    }
    public void chamaQuest1(final QuestionarioQCM questionario)
    {    	
    	setContentView(R.layout.quest1);
    	
    	RadioGroup quest1_rg = (RadioGroup) findViewById(R.id.quest1_rg);
    	final RadioButton quest1_rS = (RadioButton) findViewById(R.id.quest1_rS), quest1_rN = (RadioButton) findViewById(R.id.quest1_rN);
    	final EditText quest1_etQual = (EditText) findViewById(R.id.quest1_etQual);  
    	Button quest1_btProximo = (Button) findViewById(R.id.quest1_btProximo), quest1_btMenu = (Button) findViewById(R.id.quest1_btMenu);	
    	
    	if(questionario.quest1_sabeExame == 1){
    		quest1_rg.check(R.id.quest1_rS);
    		if(questionario.quest1_qual != "-1"){
    			quest1_etQual.setEnabled(true);
    			quest1_etQual.setText(questionario.quest1_qual);
    		}
    	}
    	else if(questionario.quest1_sabeExame == 0){
    		quest1_rg.check(R.id.quest1_rN);
    		quest1_etQual.setEnabled(false);
    	}
     	else
    		quest1_etQual.setEnabled(false);
    	
    	quest1_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
						    	
				boolean sim = R.id.quest1_rS == checkedId;
		    	boolean nao = R.id.quest1_rN == checkedId;
		    	
		    	if(sim){		    		
		    		quest1_etQual.setEnabled(true);
		    	}
		    	else if(nao){
		    		quest1_etQual.setText("");
		    		quest1_etQual.setEnabled(false);		    	
		    	}
		    }
		});
    	quest1_btProximo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest1_rS.isChecked()==false && quest1_rN.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest1(questionario);					
				}
				else if(quest1_rS.isChecked() && quest1_etQual.isEnabled() == false){
					questionario.quest1_sabeExame = 1;
					chamaQuest2(questionario);
				}
				if(quest1_rN.isChecked()){
					questionario.quest1_sabeExame = 0;
					chamaQuest2(questionario);
				}
				else{
					questionario.quest1_sabeExame = 1;
					if(quest1_etQual.isEnabled())
						if(quest1_etQual.getText().toString().length() == 0)
							ExibirMensagemOK("ERRO!", "O CAMPO (Se SIM, Qual ou quais?) DEVE ESTAR PREENCHIDO PARA PROSSEGUIR!");
					else{
						if(quest1_etQual.getText().toString().length() < 6){
							quest1_etQual.setText("");
							ExibirMensagemOK("ERRO!", "O CAMPO (Se SIM, Qual ou quais?) DEVE CONTER NO MÍNIMO 6 CARACTERES!");
						}
						else{
							questionario.quest1_qual = quest1_etQual.getText().toString();
							chamaQuest2(questionario);
						}
					}
				}					
			}
		});
    	quest1_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest1_rS.isChecked()==false && quest1_rN.isChecked()==false){
					questionario.quest1_sabeExame = -1;
					questionario.quest1_qual = "-1";
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 1!");
					chamaQuest2(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest1();
					chamaQuest1(questionario);
					return true;
				}
			}
		});
    	
    	quest1_btMenu.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				chamaMenu();				
			}
		});
    	
    }    
    public void chamaQuest2(final QuestionarioQCM questionario)
    {    	
    	setContentView(R.layout.quest2);
    	
    	RadioGroup quest2_rg;
    	Button quest2_btProximo = (Button) findViewById(R.id.quest2_btProximo), quest2_btAnterior= (Button) findViewById(R.id.quest2_btAnterior);
    	final RadioButton quest2_rS = (RadioButton) findViewById(R.id.quest2_rS), quest2_rN = (RadioButton) findViewById(R.id.quest2_rN);
    	quest2_rg = (RadioGroup) findViewById(R.id.quest2_rg);
    	
    	if(questionario.quest2_ouviuFalarAutoexame == 1)
    		quest2_rg.check(R.id.quest2_rS);
    	else if(questionario.quest2_ouviuFalarAutoexame == 0)
    		quest2_rg.check(R.id.quest2_rN);
    	
    	quest2_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
								
				final boolean sim = R.id.quest2_rS == checkedId;
		    	final boolean nao = R.id.quest2_rN == checkedId;
		    	
		    	if(sim){		    		
		    		questionario.quest2_ouviuFalarAutoexame = 1;
		    	}
		    	else if(nao){
		    		questionario.quest2_ouviuFalarAutoexame = 0;
		    	}
		    }
		});
    	quest2_btProximo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest2_rS.isChecked()==false && quest2_rN.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest2(questionario);					
				}
				else if(quest2_rS.isChecked())
						chamaQuest3(questionario);
					else{
						ExibirMensagemOK("AVISO!", "TODAS AS QUESTÕES RELACIONADAS A AUTOEXAME DE MAMAS FORAM PULADAS, POIS A PESSOA REPONDEU QUE NUNCA OUVIU SOBRE AUTOEXAME DE MAMAS!");
						questionario.resetQuest3();
						questionario.resetQuest4();
						questionario.resetQuest5();
						questionario.resetQuest6();
						questionario.resetQuest7();
						chamaQuest8(questionario);
					}
			}
		});
    	quest2_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest2_rS.isChecked()==false && quest2_rN.isChecked()==false){
					questionario.quest2_ouviuFalarAutoexame = -1;
					ExibirMensagemOK("AVISO!", "TODAS AS QUESTÕES RELACIONADAS A AUTOEXAME DE MAMAS FORAM PULADAS, POIS A PESSOA RECUSOU OU NÃO SOUBE RESPONDER SE JÁ OUVIU FALAR NO AUTO EXAME DE MAMAS!");
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 2!");
					questionario.resetQuest3();
					questionario.resetQuest4();
					questionario.resetQuest5();
					questionario.resetQuest6();
					questionario.resetQuest7();
					chamaQuest8(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest2();
					chamaQuest2(questionario);
					return true;
				}
			}
		});
    	
    	quest2_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				chamaQuest1(questionario);
			}
		});
    	
    }    
    public void chamaQuest3(final QuestionarioQCM questionario)
    {    	
    	setContentView(R.layout.quest3);
    	
    	RadioGroup quest3_rg;
    	Button quest3_btProximo = (Button) findViewById(R.id.quest3_btProximo), quest3_btAnterior = (Button) findViewById(R.id.quest3_btAnterior);
    	final RadioButton quest3_rS = (RadioButton) findViewById(R.id.quest3_rS), quest3_rN = (RadioButton) findViewById(R.id.quest3_rN);
    	quest3_rg = (RadioGroup) findViewById(R.id.quest3_rg);
    	
    	if(questionario.quest3_SabeRealizarAutoexame == 1)
    		quest3_rg.check(R.id.quest3_rS);
    	else if(questionario.quest3_SabeRealizarAutoexame == 0)
    		quest3_rg.check(R.id.quest3_rN);
    	
    	quest3_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
								
				final boolean sim = R.id.quest3_rS == checkedId;
		    	final boolean nao = R.id.quest3_rN == checkedId;
		    	
		    	if(sim){		    		
		    		questionario.quest3_SabeRealizarAutoexame = 1;
		    	}
		    	else if(nao){
		    		questionario.quest3_SabeRealizarAutoexame = 0;
		    	}
		    }
		});
    	quest3_btProximo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest3_rS.isChecked()==false && quest3_rN.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest3(questionario);					
				}
				else if(quest3_rS.isChecked())
					chamaQuest4(questionario);
					else{
						ExibirMensagemOK("AVISO!", "TODAS AS QUESTÕES RELACIONADAS A REALIZAÇÃO DO AUTOEXAME DE MAMA FORAM PULADAS, POIS A PESSOA RESPONDEU QUE NÃO SABE REALIZAR O AUTOEXAME DE MAMAS!");
						questionario.resetQuest4();
						questionario.resetQuest5();
						questionario.resetQuest6();
						questionario.resetQuest7();
						chamaQuest8(questionario);
					}
			}
		});
    	quest3_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest3_rS.isChecked()==false && quest3_rN.isChecked()==false){
					questionario.quest3_SabeRealizarAutoexame = -1;
					ExibirMensagemOK("AVISO!", "TODAS AS QUESTÕES RELACIONADAS A REALIZAÇÃO DO AUTOEXAME DE MAMA FORAM PULADAS, POIS A PESSOA RECUSOU OU NÃO SOUBE RESPONDER SE SABE REALIZAR O AUTOEXAME DE MAMAS!");
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 3!");
					questionario.resetQuest3();
					questionario.resetQuest4();
					questionario.resetQuest5();
					questionario.resetQuest6();
					questionario.resetQuest7();
					chamaQuest8(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest3();
					chamaQuest3(questionario);
					return true;
				}
			}
		});
    	quest3_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(questionario.quest2_ouviuFalarAutoexame != -1)
					chamaQuest2(questionario);
				else
				{
					if(questionario.quest1_sabeExame != -1)
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 1, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					else	
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA O INÍCIO DO QUESTIONÁRIO, POIS NENHUMA DAS QUESTÕES ANTERIORES À 3 FOI RESPONDIDA!");
					chamaQuest1(questionario);
				}
			}
		});
    }    
    public void chamaQuest4(final QuestionarioQCM questionario)
    {    	
    	setContentView(R.layout.quest4);
    	
    	RadioGroup quest4_rg = (RadioGroup) findViewById(R.id.quest4_rg);
    	final RadioButton quest4_rS = (RadioButton) findViewById(R.id.quest4_rS), quest4_rN = (RadioButton) findViewById(R.id.quest4_rN);
    	final EditText quest4_etOque = (EditText) findViewById(R.id.quest4_etOque);  
    	Button quest4_btProximo = (Button) findViewById(R.id.quest4_btProximo), quest4_btAnterior = (Button) findViewById(R.id.quest4_btAnterior);	
    	
    	if(questionario.quest4_SabeOQueAvaliar == 1){
    		quest4_rg.check(R.id.quest4_rS);
    		if(questionario.quest4_oque != "-1"){
    			quest4_etOque.setEnabled(true);
    			quest4_etOque.setText(questionario.quest4_oque);
    		}
    	}
    	else if(questionario.quest4_SabeOQueAvaliar == 0){
    		quest4_rg.check(R.id.quest4_rN);
    		quest4_etOque.setEnabled(false);
    	}
    	else
    		quest4_etOque.setEnabled(false);
    	
    	quest4_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
						    	
				boolean sim = R.id.quest4_rS == checkedId;
		    	boolean nao = R.id.quest4_rN == checkedId;
		    	
		    	if(sim){		    		
		    		quest4_etOque.setEnabled(true);
		    	}
		    	else if(nao){
		    		quest4_etOque.setText("");
		    		quest4_etOque.setEnabled(false);		    	
		    	}
		    }
		});
    	quest4_btProximo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest4_rS.isChecked()==false && quest4_rN.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest4(questionario);					
				}
				else if(quest4_rS.isChecked() && quest4_etOque.isEnabled() == false){
					questionario.quest4_SabeOQueAvaliar = 1;
					chamaQuest5(questionario);
				}
				if(quest4_rN.isChecked()){
					questionario.quest4_SabeOQueAvaliar = 0;
					chamaQuest5(questionario);
				}
				else{
					questionario.quest4_SabeOQueAvaliar = 1;
					if(quest4_etOque.isEnabled())
						if(quest4_etOque.getText().toString().length() == 0)
							ExibirMensagemOK("ERRO!", "O CAMPO (Se SIM, O que?) DEVE ESTAR PREENCHIDO PARA PROSSEGUIR!");
					else{
						if(quest4_etOque.getText().toString().length() < 6){
							quest4_etOque.setText("");
							ExibirMensagemOK("ERRO!", "O CAMPO (Se SIM, O que?) DEVE CONTER NO MÍNIMO 6 CARACTERES!");
						}
						else{
							questionario.quest4_oque = quest4_etOque.getText().toString();
							chamaQuest5(questionario);
						}
					}
				}					
			}
		});
    	quest4_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest4_rS.isChecked()==false && quest4_rN.isChecked()==false){
					questionario.quest4_SabeOQueAvaliar = -1;
					questionario.quest4_oque = "-1";
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 4!");
					chamaQuest5(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest4();
					chamaQuest4(questionario);
					return true;
				}
			}
		});
    	
    	quest4_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(questionario.quest3_SabeRealizarAutoexame != -1)
					chamaQuest3(questionario);
				else if(questionario.quest2_ouviuFalarAutoexame != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 2, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest2(questionario);
				}
				else
				{
					if(questionario.quest1_sabeExame != -1)
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 1, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					else	
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA O INÍCIO DO QUESTIONÁRIO, POIS NENHUMA DAS QUESTÕES ANTERIORES À 4 FOI RESPONDIDA!");
					chamaQuest1(questionario);
				}
			}
		});
    }       
    public void chamaQuest5(final QuestionarioQCM questionario) 
    {
    	setContentView(R.layout.quest5);
    	
    	final RadioGroup quest5_rg;
    	Button quest5_btProximo = (Button) findViewById(R.id.quest5_btProximo), quest5_btAnterior = (Button) findViewById(R.id.quest5_btAnterior);
    	final RadioButton quest5_rQualqPer = (RadioButton) findViewById(R.id.quest5_rQualqPer), quest5_rPerMens = (RadioButton) findViewById(R.id.quest5_rPerMens), quest5_r1SemAntPerMens = (RadioButton) findViewById(R.id.quest5_r1SemAntPerMens), quest5_r1SemApsPerMens = (RadioButton) findViewById(R.id.quest5_r1SemApsPerMens);
    	quest5_rg = (RadioGroup) findViewById(R.id.quest5_rg);
    	
    	if(questionario.quest5_qualquerPeriodo == 1)
    		quest5_rg.check(R.id.quest5_rQualqPer);
    	else if(questionario.quest5_periodoMenstrual == 1)
    		quest5_rg.check(R.id.quest5_rPerMens);
    	else if(questionario.quest5_antesPeriodoMenstrual == 1)
    		quest5_rg.check(R.id.quest5_r1SemApsPerMens);
    	else if(questionario.quest5_aposPeriodoMenstrual == 1)
    		quest5_rg.check(R.id.quest5_r1SemApsPerMens);    	
    	
    	quest5_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {	
				final boolean qualquerPerido = R.id.quest5_rQualqPer == checkedId;
		    	final boolean periodoMenstrual = R.id.quest5_rPerMens == checkedId;
		    	final boolean antPeriodoMenstrual = R.id.quest5_r1SemAntPerMens == checkedId;
		    	//final boolean posPeriodoMenstrual = R.id.quest5_r1SemApsPerMens == checkedId;
		    	
		    	if(qualquerPerido){		    		
		    		questionario.quest5_qualquerPeriodo = 1;
		    		questionario.quest5_periodoMenstrual = 0;
		    		questionario.quest5_antesPeriodoMenstrual = 0;
		    		questionario.quest5_aposPeriodoMenstrual = 0;
		    	}
		    	else if(periodoMenstrual){
		    		questionario.quest5_qualquerPeriodo = 0;
		    		questionario.quest5_periodoMenstrual = 1;
		    		questionario.quest5_antesPeriodoMenstrual = 0;
		    		questionario.quest5_aposPeriodoMenstrual = 0;
		    	}
		    	else if(antPeriodoMenstrual){
		    		questionario.quest5_qualquerPeriodo = 0;
		    		questionario.quest5_periodoMenstrual = 0;
		    		questionario.quest5_antesPeriodoMenstrual = 1;
		    		questionario.quest5_aposPeriodoMenstrual = 0;
		    	}
		    	else{
		    		questionario.quest5_qualquerPeriodo = 0;
		    		questionario.quest5_periodoMenstrual = 0;
		    		questionario.quest5_antesPeriodoMenstrual = 0;
		    		questionario.quest5_aposPeriodoMenstrual = 1;
		    	}
		    }
		});

    	quest5_btProximo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest5_rQualqPer.isChecked()==false && quest5_rPerMens.isChecked()==false && quest5_r1SemAntPerMens.isChecked()==false && quest5_r1SemApsPerMens.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest5(questionario);					
				}
				else chamaQuest6(questionario);				
			}
		});
    	quest5_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest5_rQualqPer.isChecked()==false && quest5_rPerMens.isChecked()==false && quest5_r1SemAntPerMens.isChecked()==false && quest5_r1SemApsPerMens.isChecked()==false){
					questionario.quest5_qualquerPeriodo = -1;
					questionario.quest5_periodoMenstrual = -1;
					questionario.quest5_antesPeriodoMenstrual = -1;
					questionario.quest5_aposPeriodoMenstrual = -1;
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 5!");
					chamaQuest6(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest5();	
					chamaQuest5(questionario);
					return true;
				}
			}
		});
    	
    	quest5_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(questionario.quest4_SabeOQueAvaliar != -1)
					chamaQuest4(questionario);
				else if(questionario.quest3_SabeRealizarAutoexame != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 3, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest3(questionario);
				}
				else if(questionario.quest2_ouviuFalarAutoexame != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 2, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest2(questionario);
				}
				else
				{
					if(questionario.quest1_sabeExame != -1)
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 1, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					else	
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA O INÍCIO DO QUESTIONÁRIO, POIS NENHUMA DAS QUESTÕES ANTERIORES À 5 FOI RESPONDIDA!");
					chamaQuest1(questionario);
				}								
			}
		});
	}
    public void chamaQuest6(final QuestionarioQCM questionario) 
    {
    	setContentView(R.layout.quest6);
    	
    	RadioGroup quest6_rg;
    	Button quest6_btProximo = (Button) findViewById(R.id.quest6_btProximo), quest6_btAnterior = (Button) findViewById(R.id.quest6_btAnterior);
    	final RadioButton quest6_rS = (RadioButton) findViewById(R.id.quest6_rS), quest6_rN = (RadioButton) findViewById(R.id.quest6_rN);
    	quest6_rg = (RadioGroup) findViewById(R.id.quest6_rg);
    	
    	if(questionario.quest6_realizaAutoexame == 1)
    		quest6_rg.check(R.id.quest6_rS);
    	else if(questionario.quest6_realizaAutoexame == 0)
    		quest6_rg.check(R.id.quest6_rN);
    	
    	quest6_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
								
				final boolean sim = R.id.quest6_rS == checkedId;
		    	final boolean nao = R.id.quest6_rN == checkedId;
		    	
		    	if(sim){		    		
		    		questionario.quest6_realizaAutoexame = 1;
		    	}
		    	else if(nao){
		    		questionario.quest6_realizaAutoexame = 0;
		    	}
		    }
		});
    	quest6_btProximo.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest6_rS.isChecked()==false && quest6_rN.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest6(questionario);					
				}
				else if(quest6_rN.isChecked())					
					chamaQuest7(questionario);
					else
					{
						ExibirMensagemOK("AVISO!", "A QUESTÃO 7 FOI PULADA, POIS ELA SÓ DEVE SER RESPONDIDA SE A RESPOSTA DA QUESTÃO 6 FOR NÃO!");
						questionario.resetQuest7();
						chamaQuest8(questionario);
					}
			}
		});
    	quest6_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest6_rS.isChecked()==false && quest6_rN.isChecked()==false){
					questionario.quest6_realizaAutoexame = -1;
					ExibirMensagemOK("AVISO!", "A QUESTÃO 7 FOI PULADA, POIS ELA DEPENDE DA RESPOSTA DA QUESTÃO 6!");
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 6!");
					chamaQuest8(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest6();
					chamaQuest6(questionario);
					return true;
				}
			}
		});	
    	
    	quest6_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(questionario.quest5_antesPeriodoMenstrual != -1) 
					chamaQuest5(questionario);
				else if(questionario.quest4_SabeOQueAvaliar != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 4, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest4(questionario);
				}
				else if(questionario.quest3_SabeRealizarAutoexame != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 3, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest3(questionario);
				}
				else if(questionario.quest2_ouviuFalarAutoexame != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 2, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest2(questionario);
				}
				else
				{
					if(questionario.quest1_sabeExame != -1)
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 1, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					else	
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA O INÍCIO DO QUESTIONÁRIO, POIS NENHUMA DAS QUESTÕES ANTERIORES À 6 FOI RESPONDIDA!");
					chamaQuest1(questionario);
				}								
			}
		});
	}
    public void chamaQuest7(final QuestionarioQCM questionario)
    {
    	setContentView(R.layout.quest7);
    	
    	RadioGroup quest7_rg = (RadioGroup) findViewById(R.id.quest7_rg);
    	final RadioButton quest7_rMedo = (RadioButton) findViewById(R.id.quest7_rMedo), quest7_rEsquecimento = (RadioButton) findViewById(R.id.quest7_rEsquecimento), quest7_rVergonha = (RadioButton) findViewById(R.id.quest7_rVergonha), quest7_rOutros = (RadioButton) findViewById(R.id.quest7_rOutros);
    	final EditText quest7_etOutros = (EditText) findViewById(R.id.quest7_etOutros);  
    	Button quest7_btProximo = (Button) findViewById(R.id.quest7_btProximo), quest7_btAnterior = (Button) findViewById(R.id.quest7_btAnterior);		
    	
    	if(questionario.quest7_medo == 1){
    		quest7_rg.check(R.id.quest7_rMedo);
    		quest7_etOutros.setEnabled(false);
    	}
    	else if(questionario.quest7_esquecimento == 1){
    		quest7_rg.check(R.id.quest7_rEsquecimento);
    		quest7_etOutros.setEnabled(false);
		}
    	else if(questionario.quest7_vergonha == 1){
    		quest7_rg.check(R.id.quest7_rVergonha);
    		quest7_etOutros.setEnabled(false);
    	}
    	else if(questionario.quest7_outros == 1){
    		quest7_rg.check(R.id.quest7_rOutros);
    		if(questionario.quest7_etOutros != "-1"){
    			quest7_etOutros.setEnabled(true);
    			quest7_etOutros.setText(questionario.quest7_etOutros);
    		}
    		else{
    			quest7_etOutros.setEnabled(false);
    		}
    	}
    	else    	
    		quest7_etOutros.setEnabled(false);
    	
    	quest7_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				boolean medo = R.id.quest7_rMedo == checkedId;
		    	boolean esquecimento = R.id.quest7_rEsquecimento == checkedId;
		    	boolean vergonha = R.id.quest7_rVergonha == checkedId;
		    	//boolean outros = R.id.quest7_etOutros == checkedId;		    	
		    	
		    	if(medo){		    		
		    		questionario.quest7_medo = 1;
		    		questionario.quest7_esquecimento = 0;
		    		questionario.quest7_vergonha = 0;
		    		questionario.quest7_outros = 0;
		    		quest7_etOutros.setText("");
		    		quest7_etOutros.setEnabled(false);
		    	}
		    	else if(esquecimento){
		    		questionario.quest7_medo = 0;
		    		questionario.quest7_esquecimento = 1;
		    		questionario.quest7_vergonha = 0;
		    		questionario.quest7_outros = 0;
		    		quest7_etOutros.setText("");
		    		quest7_etOutros.setEnabled(false);   	
		    	}
		    	else if(vergonha){
		    		questionario.quest7_medo = 0;
		    		questionario.quest7_esquecimento = 0;
		    		questionario.quest7_vergonha = 1;
		    		questionario.quest7_outros = 0;
		    		quest7_etOutros.setText("");
		    		quest7_etOutros.setEnabled(false);
		    	}
		    	else{
		    		questionario.quest7_medo = 0;
		    		questionario.quest7_esquecimento = 0;
		    		questionario.quest7_vergonha = 0;
		    		questionario.quest7_outros = 1;
		    		quest7_etOutros.setText("");
		    		quest7_etOutros.setEnabled(true);		    		
		    	}
		    }
		});
    	quest7_btProximo.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				if(quest7_rMedo.isChecked()==false && quest7_rEsquecimento.isChecked()==false && quest7_rVergonha.isChecked()==false && quest7_rOutros.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest7(questionario);					
				}
				else if(quest7_rMedo.isChecked() || quest7_rEsquecimento.isChecked() || quest7_rVergonha.isChecked()){
					chamaQuest8(questionario);
				}
				else{
					if(quest7_etOutros.isEnabled()){
						if(quest7_etOutros.getText().toString().length() == 0)
							ExibirMensagemOK("ERRO!", "O CAMPO (Se SIM, O que?) DEVE ESTAR PREENCHIDO PARA PROSSEGUIR!");
						else{
							if(quest7_etOutros.getText().toString().length() < 6){
								quest7_etOutros.setText("");
								ExibirMensagemOK("ERRO!", "O CAMPO (Se SIM, O que?) DEVE CONTER NO MÍNIMO 6 CARACTERES!");
							}
							else{
								questionario.quest7_etOutros = quest7_etOutros.getText().toString();
								chamaQuest8(questionario);
							}
						}
					}
					else
						chamaQuest8(questionario);						
				}				
			}					
		});
    	quest7_btProximo.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest7_rMedo.isChecked()==false && quest7_rEsquecimento.isChecked()==false && quest7_rVergonha.isChecked()==false && quest7_rOutros.isChecked()==false){
					questionario.quest7_medo = -1;
					questionario.quest7_esquecimento = -1;
					questionario.quest7_vergonha = -1;
					questionario.quest7_outros = -1;
					questionario.quest7_etOutros = "-1";
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 7!");
					chamaQuest8(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest7();
					chamaQuest7(questionario);
					return true;
				}
			}
		});
    	
    	quest7_etOutros.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest7_rOutros.isChecked())
				{	
					quest7_etOutros.setText("");
					quest7_etOutros.setEnabled(false);
					questionario.quest7_etOutros = "-1";
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER QUAIS OS OUTROS MOTIVOS DE NÃO REALIZAR O AUTOEXAME");
				}
				return true;
			}
		});
    	
    	quest7_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(questionario.quest6_realizaAutoexame != -1)
					chamaQuest6(questionario);
				else if(questionario.quest5_antesPeriodoMenstrual != -1){ 
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 5, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest5(questionario);
				}
				else if(questionario.quest4_SabeOQueAvaliar != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 4, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest4(questionario);
				}
				else if(questionario.quest3_SabeRealizarAutoexame != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 3, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest3(questionario);
				}
				else if(questionario.quest2_ouviuFalarAutoexame != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 2, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest2(questionario);
				}
				else
				{
					if(questionario.quest1_sabeExame != -1)
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 1, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					else	
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA O INÍCIO DO QUESTIONÁRIO, POIS NENHUMA DAS QUESTÕES ANTERIORES À 7 FOI RESPONDIDA!");
					chamaQuest1(questionario);
				}								
			}
		});
		
	}
    public void chamaQuest8(final QuestionarioQCM questionario)
    {
    	setContentView(R.layout.quest8);
    	
    	RadioGroup quest8_rg;
    	Button quest8_btFinalizar = (Button) findViewById(R.id.quest8_btFinalizar), quest8_btAnterior = (Button) findViewById(R.id.quest8_btAnterior);
    	final RadioButton quest8_rS = (RadioButton) findViewById(R.id.quest8_rS), quest8_rN = (RadioButton) findViewById(R.id.quest8_rN);
    	quest8_rg = (RadioGroup) findViewById(R.id.quest8_rg);
    	
    	if(questionario.quest8_homemTemCancerMama == 1)
    		quest8_rg.check(R.id.quest8_rS);
    	else if(questionario.quest8_homemTemCancerMama == 0)
    		quest8_rg.check(R.id.quest8_rN);
    	
    	quest8_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
								
				final boolean sim = R.id.quest8_rS == checkedId;
		    	final boolean nao = R.id.quest8_rN == checkedId;
		    	
		    	if(sim){		    		
		    		questionario.quest8_homemTemCancerMama = 1;
		    	}
		    	else if(nao){
		    		questionario.quest8_homemTemCancerMama = 0;
		    	}
		    }
		});
    	quest8_btFinalizar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(quest8_rS.isChecked()==false && quest8_rN.isChecked()==false){
					ExibirMensagemOK("ERRO!", "SELECIONE UMA DAS OPÇÕES!");
					chamaQuest8(questionario);					
				}
				else{
					ExibirQtdeQuestColhidos();
					salvaQuestionarioBancoDados(questionario);
					}
			}
		});
    	quest8_btFinalizar.setOnLongClickListener(new View.OnLongClickListener() {
			
			public boolean onLongClick(View v) {
				if(quest8_rS.isChecked()==false && quest8_rN.isChecked()==false){
					questionario.quest8_homemTemCancerMama = -1;
					ExibirQtdeQuestColhidos();					
					ExibirMensagemOK("AVISO!", "SUA AÇÃO IMPLICA QUE A PESSOA RECUSOU OU NÃO SOUBE RESPONDER A QUESTÃO 8!");
					salvaQuestionarioBancoDados(questionario);
					return true;
				}
				else{
					ExibirMensagemOK("ERRO!","VOCÊ SÓ PODE USAR ESTE COMANDO CASO NÃO SELECIONE OPÇÃO!");
					questionario.resetQuest8();
					chamaQuest8(questionario);
					return true;
				}
			}
		});
    
    	quest8_btAnterior.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(questionario.quest7_medo != -1)
					chamaQuest7(questionario);
				else if(questionario.quest6_realizaAutoexame != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 6, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest6(questionario);
				}
				else if(questionario.quest5_antesPeriodoMenstrual != -1){ 
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 5, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest5(questionario);
				}
				else if(questionario.quest4_SabeOQueAvaliar != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 4, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest4(questionario);
				}
				else if(questionario.quest3_SabeRealizarAutoexame != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 3, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest3(questionario);
				}
				else if(questionario.quest2_ouviuFalarAutoexame != -1){
					ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 2, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					chamaQuest2(questionario);
				}
				else
				{
					if(questionario.quest1_sabeExame != -1)
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA A QUESTÃO 1, POIS FOI A ULTIMA QUESTÃO RESPONDIDA!");
					else	
						ExibirMensagemOK("AVISO!", "VOCÊ VOLTOU PARA O INÍCIO DO QUESTIONÁRIO, POIS NENHUMA DAS QUESTÕES ANTERIORES À 8 FOI RESPONDIDA!");
					chamaQuest1(questionario);
				}								
			}
		});
    	
    }
    
    public void salvaQuestionarioBancoDados(QuestionarioQCM questionario)
    {
    	gerenciador.inserirQuestionario(questionario);
		chamaMenu();		   	
    }
    
    public void ExibirQtdeQuestColhidos()
    {
    	if(gerenciador.getQuestionariosCount() <= 1 )
			ExibirMensagemOK("PARABÉNS", "VOCÊ CONCLUIU ESTA COLETA DE DADOS, E AGORA POSSUI " + gerenciador.getQuestionariosCount() + " QUESTIONÁRIO COLHIDO!");
		else
			ExibirMensagemOK("PARABÉNS", "VOCÊ CONCLUIU ESTA COLETA DE DADOS, E AGORA POSSUI " + gerenciador.getQuestionariosCount() + " QUESTIONÁRIOS COLHIDOS!");
    }
    
    public void ExibirMensagemOK(String titulo, String texto)
    {
		AlertDialog.Builder mensagem = new AlertDialog.Builder(QuestionarioCancerMamaActivity.this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(texto);
		mensagem.setNeutralButton("OK", null);
		mensagem.show();
	}    

}