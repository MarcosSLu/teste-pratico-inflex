package application;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import entities.Funcionario;


//3. Program Principal
public class Principal {

	public static void main(String[] args) {
		//3.3 Formata Data e Numeros float padrão BR
		DateTimeFormatter fmtData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		NumberFormat fmtSalario = NumberFormat.getNumberInstance(Locale.of("pt", "BR"));
		
		//3.1 Funcionários
		System.out.println("----LISTA DE FUNCIONÁRIOS----");
		  List<Funcionario> funcionarios = new ArrayList<>();

	        String[][] dadosFuncionarios = {
	            {"Maria", "18/10/2000", "2009.44", "Operador"},
	            {"João", "12/05/1990", "2284.38", "Operador"},
	            {"Caio", "02/05/1961", "9836.14", "Coordenador"},
	            {"Miguel", "14/10/1988", "19119.88", "Diretor"},
	            {"Alice", "05/01/1995", "2234.68", "Recepcionista"},
	            {"Heitor", "19/11/1999", "1582.72", "Operador"},
	            {"Arthur", "31/03/1993", "4071.84", "Contador"},
	            {"Laura", "08/07/1994", "3017.45", "Gerente"},
	            {"Heloísa", "24/05/2003", "1606.85", "Eletricista"},
	            {"Helena", "02/09/1996", "2799.93", "Gerente"}
	        };

	        
	        for (String[] dados : dadosFuncionarios) {

	            funcionarios.add(new Funcionario(
	                dados[0],
	                LocalDate.parse(dados[1], fmtData),
	                new BigDecimal(dados[2]),
	                dados[3]
	            ));
	        }
	        
	        
	        // 3.2 - Remover João
	        funcionarios.removeIf(
	            funcionario -> funcionario.getNome().equals("João")
	        );
	        
	        for(Funcionario funcionario : funcionarios) {
				System.out.println(	
					funcionario.getNome() + " | " +
					funcionario.getDataNascimento().format(fmtData) + " | R$ " +
					fmtSalario.format(funcionario.getSalario()) + " | " +
					funcionario.getFuncao()
				);
			}
	        
	        
		//3.3 Itera o array de funcionarios
	        System.out.println("-----------------------------------");
		System.out.println("----FUNCIONÁRIO REMOVIDO----");
		for(Funcionario funcionario : funcionarios) {
			System.out.println(	
				funcionario.getNome() + " | " +
				funcionario.getDataNascimento().format(fmtData) + " | R$ " +
				fmtSalario.format(funcionario.getSalario()) + " | " +
				funcionario.getFuncao()
			);
		}
		
		//3.4 Funcionarios recebem aumento de 10%
		BigDecimal aumento = new BigDecimal("1.10");
		
		for (Funcionario funcionario : funcionarios) {
			funcionario.setSalario(
					funcionario.getSalario().multiply(aumento).setScale(2, RoundingMode.DOWN));
		}
		
		System.out.println("-----------------------------------");
		System.out.println("----SALÁRIOS COM AUMENTO DE 10%----");
		
		for(Funcionario funcionario : funcionarios) {
			System.out.println(	
					funcionario.getNome() + " | R$" +
					fmtSalario.format(funcionario.getSalario())
					
			);
		}
		
		//3.5 Agrupando funcionarios por função
		
		Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>(); 
		
		for (Funcionario funcionario : funcionarios) {
			
			funcionariosPorFuncao
				.computeIfAbsent(
						funcionario.getFuncao(),
						funcao -> new ArrayList<>()
						)
				.add(funcionario);
			}
		
		//3.6 Imprimir funcionários por função
		System.out.println("-----------------------------------");
		System.out.println("----FUNCIONÁRIOS POR FUNÇÃO----");
		
		for (Map.Entry<String, List<Funcionario>> grupo : funcionariosPorFuncao.entrySet()) {

		    System.out.println("\nFunção: " + grupo.getKey());

		    for (Funcionario funcionario : grupo.getValue()) {
		        System.out.println(" - " + funcionario.getNome());
		    }
		}
		
		//3.7 (Não consta na lista de requisitos)
		
		//3.8 Aniversariantes Mês 10 e 12
		
		System.out.println("-----------------------------------");
		System.out.println("---- ANIVERSARIANTES MÊS 10 E 12 ----");

		for (Funcionario funcionario : funcionarios) {

		    int mes = funcionario.getDataNascimento().getMonthValue();

		    if (mes == 10 || mes == 12) {
		        System.out.println(
		            funcionario.getNome() + " | " +
		            funcionario.getDataNascimento().format(fmtData)
		        );
		    }
		}
		
		//3.9 Funcionário com maior idade
		Funcionario maisVelho = funcionarios.get(0);

		for (Funcionario funcionario : funcionarios) {

		    if (funcionario.getDataNascimento()
		            .isBefore(maisVelho.getDataNascimento())) {

		        maisVelho = funcionario;
		    }
		}
		
		int idade = Period.between(
		        maisVelho.getDataNascimento(),
		        LocalDate.now()
		).getYears();

		System.out.println("-----------------------------------");
		System.out.println("---- FUNCIONÁRIO COM MAIOR IDADE ----");

		System.out.println(
		        maisVelho.getNome() + " | " +
		        idade + " anos"
		);
		
		//3.10 Funcionários em ordem alfabética

		funcionarios.sort(
		    Comparator.comparing(Funcionario::getNome)
		);

		System.out.println("-----------------------------------");
		System.out.println("---- FUNCIONÁRIOS EM ORDEM ALFABÉTICA ----");

		for (Funcionario funcionario : funcionarios) {
		    System.out.println(funcionario.getNome());
		}
		
		//3.11 Total dos salários

		BigDecimal totalSalarios = BigDecimal.ZERO;

		for (Funcionario funcionario : funcionarios) {
		    totalSalarios = totalSalarios.add(
		        funcionario.getSalario()
		    );
		}

		System.out.println("-----------------------------------");
		System.out.println("---- TOTAL DOS SALÁRIOS ----");

		System.out.println(
		    "Total: R$ " + fmtSalario.format(totalSalarios)
		);
		
		//3.12 Quantidade de salários mínimos

		BigDecimal salarioMinimo = new BigDecimal("1212.00");

		System.out.println("-----------------------------------");
		System.out.println("---- QUANTIDADE DE SALÁRIOS MÍNIMOS ----");

		for (Funcionario funcionario : funcionarios) {

		    BigDecimal quantidadeSalarios = funcionario
		            .getSalario()
		            .divide(salarioMinimo, 2, RoundingMode.DOWN);

		    System.out.println(
		            funcionario.getNome() + " | " +
		            quantidadeSalarios + " | recebe salários mínimos"
		    );
		}
		
		} //Fecha a Main

} //Fecha a Program


