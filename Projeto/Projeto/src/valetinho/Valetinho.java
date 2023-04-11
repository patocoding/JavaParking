package valetinho;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class Valetinho extends JFrame {
    private Estacionamento estacionamento;
    private JTextField textFieldVaga;
    private JTextField textFieldPlaca;
    private JTextArea textAreaOutput;

    public Valetinho(int numVagas) {
        estacionamento = new Estacionamento(numVagas);
        // Configurar a janela
        setTitle("Valetinho - Gerenciador de Estacionamento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configurar os componentes da interface gráfica
        textFieldVaga = new JTextField(10);
        textFieldPlaca = new JTextField(10);
        textAreaOutput = new JTextArea(10, 20);
        textAreaOutput.setEditable(false);

        JButton buttonEntrada = new JButton("Entrada");
        JButton buttonSaida = new JButton("Saída");
        JButton buttonConsulta = new JButton("Consulta de Placa");
        JButton buttonTransferencia = new JButton("Transferência de Placa");
        JButton buttonListagemGeral = new JButton("Listagem Geral");
        JButton buttonListagemLivres = new JButton("Listagem de Vagas Livres");

        // Adicionar os componentes à janela
        JPanel panelInput = new JPanel();
        panelInput.add(new JLabel("Vaga: "));
        panelInput.add(textFieldVaga);
        panelInput.add(new JLabel("Placa: "));
        panelInput.add(textFieldPlaca);
        panelInput.add(buttonEntrada);
        panelInput.add(buttonSaida);
        panelInput.add(buttonConsulta);
        panelInput.add(buttonTransferencia);
        panelInput.add(buttonListagemGeral);
        panelInput.add(buttonListagemLivres);

        JScrollPane scrollPane = new JScrollPane(textAreaOutput);

        add(panelInput, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Adicionar os ActionListeners aos botões
        buttonEntrada.addActionListener(new MeuActionListener());
        buttonSaida.addActionListener(new MeuActionListener());
        buttonConsulta.addActionListener(new MeuActionListener());
        buttonTransferencia.addActionListener(new MeuActionListener());
        buttonListagemGeral.addActionListener(new MeuActionListener());
        buttonListagemLivres.addActionListener(new MeuActionListener());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Create and show the GUI
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Specify the number of parking spaces as an argument
                int numVagas = 10; // You can change this to the desired number of parking spaces
                new Valetinho(numVagas);
            }
        });
    }	
    
	public class MeuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
	        String command = e.getActionCommand();

	        switch (command) {
	            case "Entrada":
	                try {
	                    int vagaEntrada = Integer.parseInt(textFieldVaga.getText());
	                    String placaEntrada = textFieldPlaca.getText();
	                    if (estacionamento.consultarPlaca(placaEntrada) != -1) {
	                        JOptionPane.showMessageDialog(getComponent(0), "Placa já existe no estacionamento.", "Erro", JOptionPane.ERROR_MESSAGE);
	                    } else {
	                        estacionamento.entrar(placaEntrada, vagaEntrada);
	                        textAreaOutput.append("Entrada registrada - Vaga: " + vagaEntrada + ", Placa: " + placaEntrada + "\n");
	                    }
	                } catch (NumberFormatException ex) {
	                    JOptionPane.showMessageDialog(getComponent(0), "Vaga inválida. Digite um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
	                }
	                break;
	            case "Saída":
	                try {
	                    int vagaSaida = Integer.parseInt(textFieldVaga.getText());
	                    estacionamento.sair(vagaSaida);
	                    textAreaOutput.append("Saída registrada - Vaga: " + vagaSaida + "\n");
	                } catch (NumberFormatException ex) {
	                    JOptionPane.showMessageDialog(getComponent(0), "Vaga inválida. Digite um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
	                }
	                break;
	            case "Consulta de Placa":
	                String placaConsulta = textFieldPlaca.getText();
	                int vagaConsulta = estacionamento.consultarPlaca(placaConsulta);
	                if (vagaConsulta != -1) {
                    textAreaOutput.append("Placa " + placaConsulta + " está estacionada na Vaga " + vagaConsulta + "\n");
                } else {
                    textAreaOutput.append("Placa " + placaConsulta + " não encontrada no estacionamento\n");
                }
                break;
            case "Transferência de Placa":
                try {
                    int vagaOrigem = Integer.parseInt(textFieldVaga.getText());
                    int vagaDestino = Integer.parseInt(textFieldPlaca.getText());
                    estacionamento.transferir(vagaOrigem, vagaDestino);
                    textAreaOutput.append("Placa transferida da Vaga " + vagaOrigem + " para a Vaga " + vagaDestino + "\n");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(getComponent(0), "Vaga inválida. Digite um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Listagem Geral":
                String[] placas = estacionamento.listarGeral();
                textAreaOutput.append("Listagem Geral:\n");
                for (int i = 0; i < placas.length; i++) {
                    textAreaOutput.append("Vaga " + (i + 1) + ": " + placas[i] + "\n");
                }
                break;
            case "Listagem de Vagas Livres":
                ArrayList<Integer> vagasLivres = estacionamento.listarLivres();
                textAreaOutput.append("Listagem de Vagas Livres:\n");
                for (Integer vaga : vagasLivres) {
                    textAreaOutput.append("Vaga " + vaga + " está livre\n");
                }
                break;
            default:
                break;
	        	}
	
			}

		}
	}

