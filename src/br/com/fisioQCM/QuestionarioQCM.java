package br.com.fisioQCM;

public class QuestionarioQCM {
	
	public Integer quest1_sabeExame;
	public String quest1_qual;
	
	public Integer quest2_ouviuFalarAutoexame;
	
	public Integer quest3_SabeRealizarAutoexame;
	
	public Integer quest4_SabeOQueAvaliar;
	public String quest4_oque;	
	
	public Integer quest5_qualquerPeriodo;
	public Integer quest5_periodoMenstrual;
	public Integer quest5_antesPeriodoMenstrual;
	public Integer quest5_aposPeriodoMenstrual;
	
	public Integer quest6_realizaAutoexame;
	
	public Integer quest7_medo;
	public Integer quest7_esquecimento;
	public Integer quest7_vergonha;
	public Integer quest7_outros;
	public String quest7_etOutros;
	
	public Integer quest8_homemTemCancerMama;
	
	public QuestionarioQCM()
	{
		
	}
	
	final public void resetQuestionario()
	{
		quest1_sabeExame = -1;
		quest1_qual = "-1";
		
		quest2_ouviuFalarAutoexame = -1;
		
		quest3_SabeRealizarAutoexame = -1;
		
		quest4_SabeOQueAvaliar = -1;
		quest4_oque = "-1";	
		
		quest5_qualquerPeriodo = -1;
		quest5_periodoMenstrual = -1;
		quest5_antesPeriodoMenstrual = -1;
		quest5_aposPeriodoMenstrual = -1;
		
		quest6_realizaAutoexame = -1;
		
		quest7_medo =  -1;
		quest7_esquecimento =  -1;
		quest7_vergonha = -1;
		quest7_outros =  -1;
		quest7_etOutros = "-1";	
		
		quest8_homemTemCancerMama = -1;		
	}

	final public void resetQuest1()
	{
		quest1_sabeExame = -1;
		quest1_qual = "-1";
	}
	final public void resetQuest2()
	{
		quest2_ouviuFalarAutoexame = -1;
	}
	final public void resetQuest3()
	{
		quest3_SabeRealizarAutoexame = -1;
	}
	final public void resetQuest4()
	{
		quest4_SabeOQueAvaliar = -1;
		quest4_oque = "-1";	
	}
	final public void resetQuest5()
	{
		quest5_qualquerPeriodo = -1;
		quest5_periodoMenstrual = -1;
		quest5_antesPeriodoMenstrual = -1;
		quest5_aposPeriodoMenstrual = -1;
	}
	final public void resetQuest6()
	{
		quest6_realizaAutoexame = -1;
	}
	final public void resetQuest7()
	{
		quest7_medo =  -1;
		quest7_esquecimento =  -1;
		quest7_vergonha = -1;
		quest7_outros =  -1;
		quest7_etOutros = "-1";	
	}
	final public void resetQuest8()
	{
		quest8_homemTemCancerMama = -1;	
	}
}
