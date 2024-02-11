package Views;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import Controller.ReservasController;
import Modelo.Reservas;

import java.util.Currency;
import java.util.Date;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.border.LineBorder;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class ReservasView extends JFrame {

    private JPanel contentPane;
    private JTextField txtValor;
    private JDateChooser txtDataE;
    private JDateChooser txtDataS;
    private JComboBox<String> txtFormaPagamento;
    int xMouse, yMouse;
    private JLabel lblValorSimbolo;
    private JLabel labelExit;
    private JLabel labelAtras;
    private ReservasController reservasControl;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ReservasView frame = new ReservasView();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ReservasView() {
        super("Reserva");

        this.reservasControl = new ReservasController();
        
        setIconImage(Toolkit.getDefaultToolkit().getImage(ReservasView.class.getResource("/Imagens/aH-40px.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 910, 560);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.control);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBorder(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, 910, 560);
        contentPane.add(panel);
        panel.setLayout(null);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(SystemColor.textHighlight);
		separator_1_2.setBounds(68, 195, 289, 2);
		separator_1_2.setBackground(SystemColor.textHighlight);
		panel.add(separator_1_2);
		
		JSeparator separator_1_3 = new JSeparator();
		separator_1_3.setForeground(SystemColor.textHighlight);
		separator_1_3.setBackground(SystemColor.textHighlight);
		separator_1_3.setBounds(68, 453, 289, 2);
		panel.add(separator_1_3);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setForeground(SystemColor.textHighlight);
		separator_1_1.setBounds(68, 281, 289, 11);
		separator_1_1.setBackground(SystemColor.textHighlight);
		panel.add(separator_1_1);
		
		txtDataE = new JDateChooser();
		txtDataE.getCalendarButton().setBackground(SystemColor.textHighlight);
		txtDataE.getCalendarButton().setIcon(new ImageIcon(ReservasView.class.getResource("/Imagens/icon-reservas.png")));
		txtDataE.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 12));
		txtDataE.setBounds(68, 161, 289, 35);
		txtDataE.getCalendarButton().setBounds(268, 0, 21, 33);
		txtDataE.setBackground(Color.WHITE);
		txtDataE.setBorder(new LineBorder(SystemColor.window));
		txtDataE.setDateFormatString("dd-MM-yyyy");
		txtDataE.setFont(new Font("Roboto", Font.PLAIN, 18));
		panel.add(txtDataE);
		
		JLabel lblCheckIn = new JLabel("DATA DE CHECK IN");
		lblCheckIn.setForeground(SystemColor.textInactiveText);
		lblCheckIn.setBounds(68, 136, 169, 14);
		lblCheckIn.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblCheckIn);
		
		JLabel lblCheckOut = new JLabel("DATA DE CHECK OUT");
		lblCheckOut.setForeground(SystemColor.textInactiveText);
		lblCheckOut.setBounds(68, 221, 187, 14);
		lblCheckOut.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblCheckOut);
		
		txtDataS = new JDateChooser();
		txtDataS.getCalendarButton().setIcon(new ImageIcon(ReservasView.class.getResource("/Imagens/icon-reservas.png")));
		txtDataS.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 11));
		txtDataS.setBounds(68, 246, 289, 35);
		txtDataS.getCalendarButton().setBounds(267, 1, 21, 31);
		txtDataS.setBackground(Color.WHITE);
		txtDataS.setFont(new Font("Roboto", Font.PLAIN, 18));
		txtDataS.setDateFormatString("dd-MM-yyyy");
		txtDataS.getCalendarButton().setBackground(SystemColor.textHighlight);
		txtDataS.setBorder(new LineBorder(new Color(255, 255, 255), 0));
		panel.add(txtDataS);
	    txtDataS.addPropertyChangeListener(new PropertyChangeListener() {
	        @Override
	        public void propertyChange(PropertyChangeEvent evt) {
	            if ("date".equals(evt.getPropertyName())) {
	                if (txtDataE.getDate() != null && txtDataS.getDate() != null) {
	                    valorReserva(txtDataE, txtDataS);
	                }
	            }
	        }
	    });

		
		txtValor = new JTextField();
		txtValor.setBackground(SystemColor.text);
		txtValor.setHorizontalAlignment(JTextField.CENTER);
		txtValor.setForeground(Color.BLACK);
		txtValor.setBounds(50, 328, 100, 33);
		txtValor.setEditable(false);
		txtValor.setFont(new Font("Roboto Black", Font.BOLD, 17));
		txtValor.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panel.add(txtValor);
		txtValor.setColumns(10);
		
		JLabel lblValor = new JLabel("VALOR DA RESERVA");
		lblValor.setForeground(SystemColor.textInactiveText);
		lblValor.setBounds(72, 303, 196, 14);
		lblValor.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblValor);
		
		txtFormaPagamento = new JComboBox();
		txtFormaPagamento.setBounds(68, 417, 289, 38);
		txtFormaPagamento.setBackground(SystemColor.text);
		txtFormaPagamento.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		txtFormaPagamento.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtFormaPagamento.setModel(new DefaultComboBoxModel(new String[] {"Cartão de Crédito", "Cartão de Débito", "Dinheiro"}));
		panel.add(txtFormaPagamento);
		
		JLabel lblFormaPago = new JLabel("FORMA DE PAGAMENTO");
		lblFormaPago.setForeground(SystemColor.textInactiveText);
		lblFormaPago.setBounds(68, 382, 213, 24);
		lblFormaPago.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		panel.add(lblFormaPago);
		
		JLabel lblTitulo = new JLabel("SISTEMA DE RESERVAS");
		lblTitulo.setBounds(109, 60, 219, 42);
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Roboto", Font.BOLD, 18));
		panel.add(lblTitulo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(428, 0, 482, 560);
		panel_1.setBackground(new Color(12, 138, 199));
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel logo = new JLabel("");
		logo.setBounds(197, 68, 104, 107);
		panel_1.add(logo);
		logo.setIcon(new ImageIcon(ReservasView.class.getResource("/Imagens/Ha-100px.png")));
		
		JLabel imagenFondo = new JLabel("");
		imagenFondo.setBounds(0, 140, 500, 409);
		panel_1.add(imagenFondo);
		imagenFondo.setBackground(Color.WHITE);
		imagenFondo.setIcon(new ImageIcon(ReservasView.class.getResource("/Imagens/reservas-img-3.png")));
		
		JPanel btnExit = new JPanel();
		btnExit.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente sair da aplicação?", "Confirmação", JOptionPane.YES_NO_OPTION);
		        if (resposta == JOptionPane.YES_OPTION) {
		            MenuPrincipal menuPrincipal = new MenuPrincipal();
		            menuPrincipal.setVisible(true);
		            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(btnExit);
		            frame.dispose();
		        }
		    }
			@Override
			public void mouseEntered(MouseEvent e) {
				btnExit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnExit.setBackground(new Color(12, 138, 199));
			     labelExit.setForeground(Color.white);
			}
		});
		btnExit.setLayout(null);
		btnExit.setBackground(new Color(12, 138, 199));
		btnExit.setBounds(429, 0, 53, 36);
		panel_1.add(btnExit);
		
		labelExit = new JLabel("X");
		labelExit.setForeground(Color.WHITE);
		labelExit.setBounds(0, 0, 53, 36);
		btnExit.add(labelExit);
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel header = new JPanel();
		header.setBounds(0, 0, 910, 36);
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		panel.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(SystemColor.textHighlight);
		separator_1.setBounds(68, 362, 289, 2);
		separator_1.setBackground(SystemColor.textHighlight);
		panel.add(separator_1);
		
		JPanel btnProximo = new JPanel();
        btnProximo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (txtDataE.getDate() != null && txtDataS.getDate() != null) {
                    salvarReserva();
                } else {
                    JOptionPane.showMessageDialog(null, "Deve preencher todos os campos.");
                }
            }
        });
        btnProximo.setLayout(null);
        btnProximo.setBackground(SystemColor.textHighlight);
        btnProximo.setBounds(238, 493, 122, 35);
        panel.add(btnProximo);
        btnProximo.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblSeguinte = new JLabel("PRÓXIMO");
        lblSeguinte.setHorizontalAlignment(SwingConstants.CENTER);
        lblSeguinte.setForeground(Color.WHITE);
        lblSeguinte.setFont(new Font("Roboto", Font.PLAIN, 18));
        lblSeguinte.setBounds(0, 0, 122, 35);
        btnProximo.add(lblSeguinte);
    }

    public void salvarReserva() {
        if (dadosValidos()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                LocalDate dataE = LocalDate.parse(((JTextField) txtDataE.getDateEditor().getUiComponent()).getText(), formatter);
                LocalDate dataS = LocalDate.parse(((JTextField) txtDataS.getDateEditor().getUiComponent()).getText(), formatter);

                if (dataE.isAfter(dataS)) {
                    JOptionPane.showMessageDialog(null, "A data de check-in não pode ser maior que a data de check-out.", "Erro nas datas", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LocalDate hoje = LocalDate.now();
                if (dataE.isBefore(hoje)) {
                    JOptionPane.showMessageDialog(null, "Não é possível fazer check-in em uma data passada.", "Erro na data de check-in", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Reservas reserva = new Reservas(dataE, dataS, txtValor.getText(), txtFormaPagamento.getSelectedItem().toString());
                boolean reservaSalva = reservasControl.guardarR(reserva);

                if (reservaSalva) {
                    JOptionPane.showMessageDialog(null, "Reserva salva com sucesso!");
                    int idReserva = reserva.getId();
                    RegistroHospede registro = new RegistroHospede(idReserva);
                    registro.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar a reserva. Verifique os dados e tente novamente.");
                }
            } catch (DateTimeException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao converter a data. Verifique o formato (dd-MM-yyyy) e tente novamente.");
            }
        }
    }
    
    private boolean dadosValidos() {
        return txtDataE.getDate() != null && txtDataS.getDate() != null && !txtValor.getText().equals("")
                && !txtFormaPagamento.getSelectedItem().toString().equals("");
    }


    public void valorReserva(JDateChooser dataE, JDateChooser dataS) {
        if (dataE.getDate() != null && dataS.getDate() != null) {
            Date checkInDate = dataE.getDate();
            Date checkOutDate = dataS.getDate();

            if (checkInDate.after(checkOutDate)) {
                JOptionPane.showMessageDialog(null, "A data de check-out não pode ser posterior à data de check-in", "Erro nas datas", JOptionPane.ERROR_MESSAGE);
                limparValor();
                return;
            }

            Date today = new Date();
            if (checkInDate.before(today)) {
                JOptionPane.showMessageDialog(null, "Não é possível fazer check-in em uma data passada", "Erro na data de check-in", JOptionPane.ERROR_MESSAGE);
                limparValor();
                return;
            }

            long dias = calcularDiferencaDias(checkInDate, checkOutDate);
            int noite = 80;
            int valor = (int) dias * noite;

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
            currencyFormat.setCurrency(Currency.getInstance("BRL"));
            txtValor.setText(currencyFormat.format(valor));
        }
    }


    private long calcularDiferencaDias(Date dataInicio, Date dataFim) {
        long diferenca = dataFim.getTime() - dataInicio.getTime();
        return TimeUnit.DAYS.convert(diferenca, TimeUnit.MILLISECONDS);
    }

	public void limparValor() {
		txtValor.setText("");
	}
	
    
	//Código que permite movimentar a janela pela tela seguindo a posição de "x" e "y"	
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
}
