package Views;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Controller.HospedesController;
import Controller.ReservasController;
import Modelo.Hospedes;
import Modelo.Reservas;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import java.text.NumberFormat;

@SuppressWarnings("serial")
public class Buscar extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHospedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHospedes;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	
	private ReservasController reservasControl;
	private HospedesController hospedesControl;
	private ReservasView reservasView;
	String reservas;
	String hospedes;
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Buscar frame = new Buscar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Buscar() {
		
		this.reservasControl = new ReservasController();
		this.hospedesControl = new HospedesController();
		this.reservasView = new ReservasView();
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Buscar.class.getResource("/Imagens/lOGO-50PX.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblTitulo = new JLabel("SISTEMA DE BUSCA");
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblTitulo.setBounds(331, 62, 280, 42);
		contentPane.add(lblTitulo);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);
				
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Data Check In");
		modelo.addColumn("Data Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		tbReservas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Buscar.class.getResource("/Imagens/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		mostrarTabelaReservas();
		
		tbHospedes = new JTable();
		tbHospedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHospedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHospedes = (DefaultTableModel) tbHospedes.getModel();
		modeloHospedes.addColumn("Numero de Hóspede");
		modeloHospedes.addColumn("Nome");
		modeloHospedes.addColumn("Sobrenome");
		modeloHospedes.addColumn("Data de Nascimento");
		modeloHospedes.addColumn("Nacionalidade");
		modeloHospedes.addColumn("Telefone");
		modeloHospedes.addColumn("Numero de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHospedes);
		panel.addTab("Hospedes", new ImageIcon(Buscar.class.getResource("/Imagens/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		mostrarTabelaHospedes();

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Buscar.class.getResource("/Imagens/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
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
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
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
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel bntExit = new JPanel();
		bntExit.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente sair da aplicação?", "Confirmação", JOptionPane.YES_NO_OPTION);
		        if (resposta == JOptionPane.YES_OPTION) {
		            MenuPrincipal menuPrincipal = new MenuPrincipal();
		            menuPrincipal.setVisible(true);
		            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(bntExit);
		            frame.dispose();
		        }
		    }
			@Override
			public void mouseEntered(MouseEvent e) {
				bntExit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { 
				 bntExit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		bntExit.setLayout(null);
		bntExit.setBackground(Color.WHITE);
		bntExit.setBounds(857, 0, 53, 36);
		header.add(bntExit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		bntExit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limparTabelaReservas();
				limparTabelaHospedes();
				if(txtBuscar.getText().equals("")) {
					mostrarTabelaReservas();
					mostrarTabelaHospedes();
				}else {
					mostrarTabelaReservasId();
					mostrarTabelaHospedesID();
				}
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		btnEditar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int listaReservas = tbReservas.getSelectedRow();
				int listaHospedes = tbHospedes.getSelectedRow();
				
				if(listaReservas >= 0) {
					atualizarReservas();
					limparTabelaReservas();
					limparTabelaHospedes();
					mostrarTabelaReservas();
					mostrarTabelaHospedes();
				}else if (listaHospedes >= 0) {
					atualizarHospedes();
					limparTabelaReservas();
					limparTabelaHospedes();
					mostrarTabelaHospedes();
					mostrarTabelaReservas();
				}
			}
		});
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnDeletar = new JPanel();
		btnDeletar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int listaReservas = tbReservas.getSelectedRow();
				int listaHospedes = tbHospedes.getSelectedRow();
				
				if(listaReservas >= 0) {
					reservas = tbReservas.getValueAt(listaReservas, 0).toString();
					int confirmarR = JOptionPane.showConfirmDialog(null, "Deseja excluir a reserva?");
					if (confirmarR == JOptionPane.YES_OPTION) {
						String valor = tbReservas.getValueAt(listaReservas, 0).toString();
						reservasControl.excluirR(Integer.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane, "Reserva apagada com sucesso!");
						limparTabelaReservas();
						limparTabelaHospedes();
						mostrarTabelaReservas();
						mostrarTabelaHospedes();
					}
				}else if(listaHospedes >= 0) {
					hospedes = tbHospedes.getValueAt(listaHospedes, 0).toString();
					int confirmarH = JOptionPane.showConfirmDialog(null, "Deseja excluir o hóspede?");
					
					if(confirmarH == JOptionPane.YES_OPTION) {
						String valor = tbHospedes.getValueAt(listaHospedes, 0).toString();
						hospedesControl.excluirH(Integer.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane, "Hóspede excluido com sucesso!");
						limparTabelaHospedes();
						limparTabelaReservas();
						mostrarTabelaHospedes();
						mostrarTabelaReservas();
						
					}
				}else {
					JOptionPane.showMessageDialog(null, "Houve um erro ao tentar excluir a reserva");
				}
			}
		});
		btnDeletar.setLayout(null);
		btnDeletar.setBackground(new Color(12, 138, 199));
		btnDeletar.setBounds(767, 508, 122, 35);
		btnDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnDeletar);
		
		JLabel lblExcluir = new JLabel("DELETAR");
		lblExcluir.setHorizontalAlignment(SwingConstants.CENTER);
		lblExcluir.setForeground(Color.WHITE);
		lblExcluir.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblExcluir.setBounds(0, 0, 122, 35);
		btnDeletar.add(lblExcluir);
		setResizable(false);
		}
	
	
	 	private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	 	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
	    }
	    
	 	
	 // CONTROLE RESERVAS
	 	private List<Reservas> mostrarReservas() {
	 	    return this.reservasControl.mostrarR();
	 	}

	 	private List<Reservas> buscarIdReserva() {
	 	    return this.reservasControl.buscarR(txtBuscar.getText());
	 	}

	 	private void limparTabelaReservas() {
	 	    ((DefaultTableModel) tbReservas.getModel()).setRowCount(0);
	 	}

	 	private void mostrarTabelaReservas() {
	 	    List<Reservas> reservas = mostrarReservas();
	 	    try {
	 	        for (Reservas reserva : reservas) {
	 	            modelo.addRow(new Object[] {
	 	                    reserva.getId(),
	 	                    reserva.getDataE().format(DATE_FORMATTER),
	 	                    reserva.getDataS().format(DATE_FORMATTER),
	 	                    reserva.getValor(),
	 	                    reserva.getFormaPag()
	 	            });
	 	        }
	 	    } catch (Exception e) {
	 	        throw new RuntimeException(e);
	 	    }
	 	}

	 	private void mostrarTabelaReservasId() {
	 	    List<Reservas> reservas = buscarIdReserva();
	 	    try {
	 	        for (Reservas reserva : reservas) {
	 	            modelo.addRow(new Object[] {
	 	                    reserva.getId(),
	 	                    reserva.getDataE().format(DATE_FORMATTER),
	 	                    reserva.getDataS().format(DATE_FORMATTER),
	 	                    reserva.getValor(),
	 	                    reserva.getFormaPag()
	 	            });
	 	        }
	 	    } catch (Exception e) {
	 	        throw new RuntimeException(e);
	 	    }
	 	}

	 	private void atualizarReservas() {
	 	    LocalDate dataE;
	 	    LocalDate dataS;

	 	    try {
	 	        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	 	        dataE = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString(), dateFormat);
	 	        dataS = LocalDate.parse(modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString(), dateFormat);
	 	    } catch (DateTimeException e) {
	 	        throw new RuntimeException("Erro ao parsear datas", e);
	 	    }

	 	    if (dataE.isAfter(dataS)) {
	 	        JOptionPane.showMessageDialog(this, "A data de check-in não pode ser maior que a data de check-out", "Erro nas datas", JOptionPane.ERROR_MESSAGE);
	 	    } else if (dataE.isBefore(LocalDate.now())) {
	 	        JOptionPane.showMessageDialog(this, "Não é possível fazer check-in em uma data passada", "Erro na data de check-in", JOptionPane.ERROR_MESSAGE);
	 	    } else {
	 	        this.reservasView.limparValor();

	 	        String valor = calcularValorReserva(dataE, dataS);
	 	        String formaPag = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 4);
	 	        Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());

	 	        if (tbReservas.getSelectedColumn() == 0) {
	 	            JOptionPane.showMessageDialog(this, "Não é possível mudar os ID");
	 	        } else {
	 	            this.reservasControl.atualizarR(dataE, dataS, valor, formaPag, id);
	 	            JOptionPane.showMessageDialog(this, String.format("Reserva modificada com sucesso"));
	 	        }
	 	    }
	 	}

	 public String calcularValorReserva(LocalDate dataE, LocalDate dataS) {
	     if (dataE != null && dataS != null) {
	         int dias = (int) ChronoUnit.DAYS.between(dataE, dataS);
	         int noite = 80;
	         int valor = dias * noite;

	         NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
	         currencyFormat.setCurrency(Currency.getInstance("BRL"));
	         return currencyFormat.format(valor);
	     } else {
	         return "";
	     }
	 }


	    //CONTROLE HOSPEDES
	    private List<Hospedes> MostrarHospedes() {
	    	return this.hospedesControl.mostrarH();
	    }
	    
	    private List<Hospedes> buscarIDhospedes() {
	    	return this.hospedesControl.buscarH(txtBuscar.getText());
	    }
	    
	    private void limparTabelaHospedes() {
	 	    ((DefaultTableModel) tbHospedes.getModel()).setRowCount(0);
	 	}
    	 
	    private void mostrarTabelaHospedes() {
	    	List<Hospedes> hospedes = MostrarHospedes();
	    	try {
	    		for (Hospedes hospede : hospedes) {
	    			modeloHospedes.addRow(new Object[] {
	    					hospede.getId(),
	    					hospede.getNome(),
	    					hospede.getSobrenome(),
	    					hospede.getDataNascimento().format(DATE_FORMATTER),
	    					hospede.getNacionalidade(),
	    					hospede.getTelefone(),
	    					hospede.getIdReserva()
	    			});
	    		}
	    	}catch (Exception e) {
	    		
	    	}
	    }
	    
	    private void mostrarTabelaHospedesID() {
	    	List<Hospedes> hospedes = buscarIDhospedes();
	    	try {
	    		for (Hospedes hospede : hospedes) {
	    			modeloHospedes.addRow(new Object[] {
	    					hospede.getId(),
	    					hospede.getNome(),
	    					hospede.getSobrenome(),
	    					hospede.getDataNascimento().format(DATE_FORMATTER),
	    					hospede.getNacionalidade(),
	    					hospede.getTelefone(),
	    					hospede.getIdReserva()
	    			});
	    		}
	    	}catch (Exception e) {
	    		
	    	}
	    }
	  
	    private void atualizarHospedes() {
	    	Optional.ofNullable(modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), tbHospedes.getSelectedColumn()))
	    	.ifPresentOrElse(listaHospedes -> {
	    		
	    		String nome = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 1);
	    		String sobrenome = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 2);
	    		
	    		LocalDate dataNascimento;
	    		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    		dataNascimento = LocalDate.parse(modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 3).toString(), dateFormat);
	    		
	    		String nacionalidade = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 4);
	    		String telefone = (String) modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 5);
	    		Integer idReserva = Integer.valueOf(modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 6).toString());
	    		Integer id = Integer.valueOf(modeloHospedes.getValueAt(tbHospedes.getSelectedRow(), 0).toString());
	    		
	    		if(tbHospedes.getSelectedColumn() == 0 || tbHospedes.getSelectedColumn() == 6) {
	    			JOptionPane.showMessageDialog(this, "Não é possível mudar os ID");
	    		}else {
	    			this.hospedesControl.atualizarH(nome, sobrenome, dataNascimento, nacionalidade, telefone, idReserva, id);
		    		JOptionPane.showMessageDialog(this, String.format("Reserva modificada com sucesso!"));
	    		}

	    		
	    	}, ()->  JOptionPane.showMessageDialog(this, "Não foi possível atualizar as informações do Hóspede"));
	    }
}