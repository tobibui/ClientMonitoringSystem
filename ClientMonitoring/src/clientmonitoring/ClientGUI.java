/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clientmonitoring;

import static com.sun.nio.file.ExtendedWatchEventModifier.FILE_TREE;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author tuyen
 */
public class ClientGUI extends javax.swing.JFrame {
    public ClientGUI() {
        initComponents();
    }
    Socket socket = null;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputIP = new javax.swing.JTextPane();
        btnConnect = new javax.swing.JButton();
        inputFilter = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableClientLog = new javax.swing.JTable();
        inputPort = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("IP:");

        jScrollPane1.setViewportView(inputIP);

        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        jLabel2.setText("Filter:");

        tableClientLog.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#Num", "Time", "Action", "Content"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableClientLog);

        jLabel3.setText("Port:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(inputPort, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnConnect))
                            .addComponent(inputFilter))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnConnect)
                        .addComponent(inputPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    class SearchLog extends Thread {

        public SearchLog() {
        }

        @Override
        public void run() {
            try {
                TableRowSorter<TableModel> sorted = new TableRowSorter<>(tableClientLog.getModel());
                tableClientLog.setRowSorter(sorted);
                SwingUtilities.invokeLater(() -> {
                    inputFilter.getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            String inputData = inputFilter.getText();
                            sorted.setRowFilter(null);
                            if (inputData.trim().length() > 0) {
                                sorted.setRowFilter(RowFilter.regexFilter("(?i)" + inputData));
                            }
                        }
                        @Override
                        public void removeUpdate(DocumentEvent e) {
                            String str = inputFilter.getText();
                            sorted.setRowFilter(null);
                            if (str.trim().length() == 0) {
                                sorted.setRowFilter(RowFilter.regexFilter("(?i)" + str));                                
                            }
                        }
                        @Override
                        public void changedUpdate(DocumentEvent e) {
                            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                        }
                    });
                });
            } catch (Exception e) {
            }
        }
    }

    class RenewTable extends Thread {
        private final DefaultTableModel dataModel;
        public RenewTable(DefaultTableModel model) {
            this.dataModel = model;
        }
        @Override
        public void run() {
            SwingUtilities.invokeLater(() -> {
                dataModel.fireTableDataChanged();
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class ListenProcess extends Thread {

        private Socket socket;
        private String pathAction;
        private ObjectOutputStream outputStream;

        public ListenProcess(Socket socket, String Path, ObjectOutputStream oos) {
            this.socket = socket;
            this.pathAction = Path;
            this.outputStream = oos;
        }

        @Override
        public void run() {
            DateFormat currentTime = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            FileSystem fs = FileSystems.getDefault();
            WatchService watchRealtime;
            try {
                watchRealtime = fs.newWatchService();
                Path path = Paths.get(pathAction);
                path.register(watchRealtime, new WatchEvent.Kind[]{ENTRY_MODIFY, ENTRY_CREATE, ENTRY_DELETE}, FILE_TREE);
                while (true) {
                    Message newMessage = new Message();
                    WatchKey key = watchRealtime.take();
                    for (WatchEvent<?> e : key.pollEvents()) {
                        Object context = e.context();
                        
                        newMessage.setTime(currentTime.format(new Date()));
                        newMessage.setAction(e.kind().name());
                        newMessage.setDescription(context.toString(), e.kind().name());
                        DefaultTableModel model = (DefaultTableModel) tableClientLog.getModel();
                        RenewTable ClientRenew = new RenewTable(model);
                        int countLine = model.getRowCount();
                        model.addRow(
                                new Object[]{
                                    countLine + 1,
                                    newMessage.getTime(),
                                    newMessage.getAction(),
                                    newMessage.getDescription()
                                }
                        );
                        ClientRenew.start();
                        saveLog(socket.getInetAddress().getHostAddress()
                                + ":"
                                + socket.getPort()
                                + " => Hành động: "
                                + newMessage.getAction()
                                + " | Thời gian: "
                                + newMessage.getTime()
                                + " | Mô tả: "
                                + newMessage.getDescription());
                        outputStream.writeObject(newMessage);
                        outputStream.flush();
                    }
                    key.reset();

                }

            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    class ConnectServer extends Thread {

        private String IPServer;
        private int PortServer;

        public ConnectServer(String ip, int port) {
            this.IPServer = ip;
            this.PortServer = port;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    socket = new Socket(IPServer, PortServer);
                } catch (IOException ex) {
                    Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                String logConnect = socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + ": Ket noi server";
                try {
                    saveLog(logConnect);
                } catch (IOException ex) {
                    Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                DataInputStream dis = null;
                try {
                    dis = new DataInputStream(socket.getInputStream());
                } catch (IOException ex) {
                    Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(socket.getOutputStream());
                } catch (IOException ex) {
                    Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                String pathFolder = null;
                try {
                    pathFolder = dis.readUTF();
                } catch (IOException ex) {
                    Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(pathFolder);
                ListenProcess mt = new ListenProcess(socket, pathFolder, oos);
                mt.start();
                return;
            }
        }
    }

    private static void saveLog(String log) throws IOException {
        String filename = "log/client.txt";
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(log + "\n");
        } catch (IOException e) {
        }
    }

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        String ipAddress = inputIP.getText();
        Integer port = Integer.parseInt(inputPort.getText());

        if (!ipAddress.isEmpty()) {
            ConnectServer connectServer = new ConnectServer(ipAddress, port);
            connectServer.start();
            SearchLog logAction = new SearchLog();
            logAction.start();
        } else {
            JOptionPane.showMessageDialog(this, "Vui long nhap IP",
                    "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnConnectActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new ClientGUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnect;
    private javax.swing.JTextField inputFilter;
    private javax.swing.JTextPane inputIP;
    private javax.swing.JTextField inputPort;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tableClientLog;
    // End of variables declaration//GEN-END:variables
}
