package GUI.Panel;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.io.File;

public class TrangChu extends JPanel {

    private static final Color TEAL_DARK = new Color(0, 139, 139);
    private static final Color BG_COLOR  = Color.WHITE;
    private static final Color CARD_BG   = Color.WHITE;
    private static final Color TEXT_TEAL = new Color(0, 160, 160);
    private static final Color TEXT_GRAY = new Color(100, 100, 100);

    public TrangChu() {
        initUI();
    }

    public void initUI() {
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);

        add(buildHeader(),     BorderLayout.NORTH);
        add(buildCardsPanel(), BorderLayout.CENTER);
    }

    // ------------------------------------------------------------------ //
    //  HEADER
    // ------------------------------------------------------------------ //
    private JPanel buildHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(BG_COLOR);
        header.setBorder(new EmptyBorder(30, 40, 10, 40));

        // Banner
        ImageIcon bannerIcon = loadImage("/GUI/image/banner-tgdd-800x300.jpg", 400, 75);
        if (bannerIcon != null) {
            JLabel logoLabel = new JLabel(bannerIcon, SwingConstants.CENTER);
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            header.add(logoLabel);
        } else {
            System.out.println("[WARN] Không tìm thấy ảnh banner!");
        }

        header.add(Box.createVerticalStrut(14));

        // Tiêu đề
        JLabel title = new JLabel(
            "<html><div style='text-align:center;'>Hệ Thống Bán Lẻ Điện Thoại</div></html>",
            SwingConstants.CENTER
        );
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(TEAL_DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.add(title);

        header.add(Box.createVerticalStrut(8));

        // Slogan
        JLabel slogan = new JLabel(
            "<html><div style='text-align:center;'>"
            + "<i>– Hãy hướng về phía mặt trời, nơi mà bóng tối luôn ở phía sau lưng bạn."
            + " Điều mà hoa hướng dương vẫn làm mỗi ngày.–</i><br>"
            + "<b>Team 3</b>"
            + "</div></html>",
            SwingConstants.CENTER
        );
        slogan.setFont(new Font("Arial", Font.PLAIN, 13));
        slogan.setForeground(new Color(50, 50, 50));
        slogan.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.add(slogan);

        header.add(Box.createVerticalStrut(20));

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(220, 220, 220));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        header.add(sep);

        return header;
    }

    // ------------------------------------------------------------------ //
    //  CARDS
    // ------------------------------------------------------------------ //
    private JPanel buildCardsPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BG_COLOR);
        wrapper.setBorder(new EmptyBorder(30, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(0, 15, 0, 15);
        gbc.fill    = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Load ảnh – thử nhiều đường dẫn khác nhau
        ImageIcon icon1 = loadImageMultiplePaths("buy",    120, 120);
        ImageIcon icon2 = loadImageMultiplePaths("invoice",      120, 120);
        ImageIcon icon3 = loadImageMultiplePaths("analyzing",120, 120);

        gbc.gridx = 0;
        wrapper.add(buildCard(
            "BÁN HÀNG",
            "Quản lý việc bán hàng, tạo đơn hàng mới và thực hiện thanh toán cho khách hàng.",
            icon1, "BanHang"
        ), gbc);

        gbc.gridx = 1;
        wrapper.add(buildCard(
            "HÓA ĐƠN",
            "Xem lại lịch sử giao dịch, chi tiết các hóa đơn đã xuất và quản lý thu chi.",
            icon2, "HoaDon"
        ), gbc);

        gbc.gridx = 2;
        wrapper.add(buildCard(
            "THỐNG KÊ",
            "Xem báo cáo doanh thu, phân tích dữ liệu bán hàng và hiệu suất kinh doanh.",
            icon3, "ThongKe"
        ), gbc);

        return wrapper;
    }

    // ------------------------------------------------------------------ //
    //  HELPER: load ảnh với nhiều đường dẫn fallback
    // ------------------------------------------------------------------ //

    private ImageIcon loadImageMultiplePaths(String baseName, int w, int h) {
        // Danh sách đường dẫn cần thử (theo thứ tự ưu tiên)
        String[] paths = {
            "/img/"          + baseName + ".png",
            "/GUI/image/"    + baseName + ".png",
            "/images/"       + baseName + ".png",
            "/resources/img/"+ baseName + ".png",
            "img/"           + baseName + ".png",   // không có dấu /
        };

        for (String path : paths) {
            java.net.URL url = getClass().getResource(path);
            if (url != null) {
                System.out.println("[OK] Tìm thấy ảnh: " + path);
                Image scaled = new ImageIcon(url).getImage()
                                   .getScaledInstance(w, h, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }
        }

        // Thử đường dẫn file hệ thống (fallback cuối)
        String[] filePaths = {
            "/img/"           + baseName + ".png",
        };
        for (String fp : filePaths) {
            File f = new File(fp);
            if (f.exists()) {
                System.out.println("[OK] Tìm thấy ảnh (file path): " + fp);
                Image scaled = new ImageIcon(fp).getImage()
                                   .getScaledInstance(w, h, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }
        }

        System.out.println("[WARN] Không tìm thấy ảnh: " + baseName + ".png");
        return null;
    }

    /**
     * Load ảnh đơn theo đường dẫn tuyệt đối trong classpath.
     */
    private ImageIcon loadImage(String path, int w, int h) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            System.out.println("[WARN] Không tìm thấy: " + path);
            return null;
        }
        Image scaled = new ImageIcon(url).getImage()
                           .getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    // ------------------------------------------------------------------ //
    //  CARD COMPONENT
    // ------------------------------------------------------------------ //
    private JPanel buildCard(String title, String description,
                             ImageIcon icon, String targetPage) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_BG);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                g2.setColor(new Color(200, 200, 200));
                g2.setStroke(new BasicStroke(1.2f));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 20, 20));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Window window = SwingUtilities.getWindowAncestor(TrangChu.this);
                if (window instanceof GUI.Main) {
                    ((GUI.Main) window).chuyenTrang(targetPage);
                }
            }
        });

        // Icon hoặc placeholder
        JLabel iconLabel;
        if (icon != null) {
            iconLabel = new JLabel(icon, SwingConstants.CENTER);
        } else {
            // Placeholder khi không tìm thấy ảnh
            iconLabel = new JLabel(getPlaceholderIcon(title), SwingConstants.CENTER);
        }
        iconLabel.setPreferredSize(new Dimension(0, 160));
        iconLabel.setBorder(new EmptyBorder(20, 20, 10, 20));
        card.add(iconLabel, BorderLayout.NORTH);

        // Text panel
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBorder(new EmptyBorder(0, 20, 25, 20));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
        titleLabel.setForeground(TEXT_TEAL);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPanel.add(titleLabel);

        textPanel.add(Box.createVerticalStrut(10));

        JTextArea descArea = new JTextArea(description);
        descArea.setFont(new Font("Arial", Font.PLAIN, 13));
        descArea.setForeground(TEXT_GRAY);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setOpaque(false);
        descArea.setEditable(false);
        descArea.setFocusable(false);
        textPanel.add(descArea);

        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    // ------------------------------------------------------------------ //
    //  PLACEHOLDER
    // ------------------------------------------------------------------ //
    private ImageIcon getPlaceholderIcon(String title) {
        int size = 80;
        java.awt.image.BufferedImage img =
            new java.awt.image.BufferedImage(size, size,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        // Nền tròn teal
        g2.setColor(new Color(0, 160, 160, 40));
        g2.fillOval(0, 0, size, size);
        g2.setColor(TEXT_TEAL);
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(1, 1, size - 2, size - 2);

        // Chữ cái đầu
        g2.setFont(new Font("Arial", Font.BOLD, 28));
        g2.setColor(TEXT_TEAL);
        String letter = title.length() > 0 ? String.valueOf(title.charAt(0)) : "?";
        FontMetrics fm = g2.getFontMetrics();
        int x = (size - fm.stringWidth(letter)) / 2;
        int y = (size - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(letter, x, y);
        g2.dispose();

        return new ImageIcon(img);
    }
}