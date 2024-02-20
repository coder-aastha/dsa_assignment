package src.Q7_social_media_app;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocialMediaApp extends JFrame {
  private JTextArea newsFeedTextArea;
  private JTextField postTextField;
  private JButton postButton;
  private JButton likeButton;
  private JButton commentButton;
  private JButton uploadButton;
  private JButton followButton;
  private JButton viewProfileButton;
  private JButton recommendButton;
  private JTextArea recommendationTextArea;

  private boolean isAuthenticated = false;
  private String currentUser;

  private static final String DB_URL = "jdbc:mysql://localhost:3306/social_media";
  private static final String DB_USER = "root";
  private static final String DB_PASSWORD = "T@ekwondo12";

  private JPanel authPanel;
  private JPanel homePanel;
  private JPanel loginPanel;
  private JPanel registerPanel;
  private JPanel followPanel;

  public SocialMediaApp() {
    setTitle("Social Media App");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    authPanel = new JPanel(new CardLayout());
    homePanel = new JPanel(new BorderLayout());

    // Shows the authentication panel initially
    showAuthenticationPanel();

    add(authPanel);
    add(homePanel);

    setLayout(new GridLayout(1, 2));
    setVisible(true);
  }

  private void showAuthenticationPanel() {
    // Creates and configures the authentication panel
    loginPanel = new JPanel(new GridLayout(3, 2));
    JTextField usernameFieldLogin = new JTextField();
    JPasswordField passwordFieldLogin = new JPasswordField();
    JButton loginButton = new JButton("Login");

    loginButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String username = usernameFieldLogin.getText();
        String password = new String(passwordFieldLogin.getPassword());

        if (authenticateUser(username, password)) {
          isAuthenticated = true;
          currentUser = username;
          remove(authPanel);
          showHomePanel();
        } else {
          JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    loginPanel.add(new JLabel("Username:"));
    loginPanel.add(usernameFieldLogin);
    loginPanel.add(new JLabel("Password:"));
    loginPanel.add(passwordFieldLogin);
    loginPanel.add(new JLabel(""));
    loginPanel.add(loginButton);

    registerPanel = new JPanel(new GridLayout(4, 2));
    JTextField usernameFieldRegister = new JTextField();
    JPasswordField passwordFieldRegister = new JPasswordField();
    JPasswordField confirmPasswordFieldRegister = new JPasswordField();
    JButton registerButton = new JButton("Register");

    registerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String username = usernameFieldRegister.getText();
        String password = new String(passwordFieldRegister.getPassword());
        String confirmPassword = new String(confirmPasswordFieldRegister.getPassword());

        if (!password.equals(confirmPassword)) {
          JOptionPane.showMessageDialog(null, "Passwords do not match", "Registration Error",
              JOptionPane.ERROR_MESSAGE);
        } else if (registerUser(username, password)) {
          isAuthenticated = true;
          currentUser = username;
          remove(authPanel);
          showHomePanel();
        } else {
          JOptionPane.showMessageDialog(null, "Username already exists", "Registration Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    registerPanel.add(new JLabel("Username:"));
    registerPanel.add(usernameFieldRegister);
    registerPanel.add(new JLabel("Password:"));
    registerPanel.add(passwordFieldRegister);
    registerPanel.add(new JLabel("Confirm Password:"));
    registerPanel.add(confirmPasswordFieldRegister);
    registerPanel.add(new JLabel(""));
    registerPanel.add(registerButton);

    followPanel = new JPanel(new GridLayout(4, 2));
    JTextField followUsernameField = new JTextField();
    followButton = new JButton("Follow");
    viewProfileButton = new JButton("View Profile");

    followButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String followUser = followUsernameField.getText();
        if (followUser != null && !followUser.isEmpty()) {
          if (followUser(followUser)) {
            JOptionPane.showMessageDialog(null, "You are now following " + followUser);
          } else {
            JOptionPane.showMessageDialog(null, "User not found or already followed");
          }
        }
      }
    });

    viewProfileButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String viewUser = followUsernameField.getText();
        if (viewUser != null && !viewUser.isEmpty()) {
          viewUserProfile(viewUser);
        }
      }
    });

    followPanel.add(new JLabel("Follow User:"));
    followPanel.add(followUsernameField);
    followPanel.add(new JLabel(""));
    followPanel.add(followButton);
    followPanel.add(new JLabel(""));
    followPanel.add(viewProfileButton);

    recommendButton = new JButton("Recommend Content");
    recommendButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        recommendContent();
      }
    });

    JPanel recommendPanel = new JPanel(new GridLayout(1, 1));
    recommendPanel.add(recommendButton);

    JPanel combinedPanel = new JPanel(new GridLayout(1, 5));
    combinedPanel.add(loginPanel);
    combinedPanel.add(registerPanel);
    combinedPanel.add(followPanel);
    combinedPanel.add(recommendPanel);
    authPanel.add(combinedPanel, "COMBINED");

    CardLayout cardLayout = (CardLayout) authPanel.getLayout();
    cardLayout.show(authPanel, "COMBINED");
  }

  private void showHomePanel() {
    // Creates and configures the home panel with news feed, post, like, comment,
    // upload, and recommendation components
    postTextField = new JTextField();
    postButton = new JButton("Post");
    likeButton = new JButton("Like");
    commentButton = new JButton("Comment");
    uploadButton = new JButton("Upload Image");

    postButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String postText = postTextField.getText();
        if (!postText.isEmpty()) {
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String timestamp = dateFormat.format(new Date());
          String post = currentUser + " (" + timestamp + "):\n" + postText + "\n\n";
          newsFeedTextArea.append(post);
          postTextField.setText("");
        }
      }
    });

    likeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Post Liked!");
      }
    });

    commentButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String comment = JOptionPane.showInputDialog(null, "Enter your comment:");
        if (comment != null && !comment.isEmpty()) {
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String timestamp = dateFormat.format(new Date());
          String commentText = currentUser + " commented on this post (" + timestamp + "):\n" + comment + "\n\n";
          newsFeedTextArea.append(commentText);
        }
      }
    });

    uploadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose an image to upload");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          saveImagePathToDB(currentUser, selectedFile.getAbsolutePath());
          displayImageInNewsFeed(selectedFile.getAbsolutePath());
          JOptionPane.showMessageDialog(null, "Image uploaded: " + selectedFile.getAbsolutePath());
        }
      }
    });

    JPanel mainPanel = new JPanel(new BorderLayout());
    newsFeedTextArea = new JTextArea();
    newsFeedTextArea.setEditable(false);
    JScrollPane newsFeedScrollPane = new JScrollPane(newsFeedTextArea);
    mainPanel.add(newsFeedScrollPane, BorderLayout.CENTER);

    JPanel postPanel = new JPanel(new BorderLayout());
    postPanel.add(postTextField, BorderLayout.CENTER);
    postPanel.add(postButton, BorderLayout.EAST);
    postPanel.add(likeButton, BorderLayout.WEST);
    postPanel.add(commentButton, BorderLayout.SOUTH);
    postPanel.add(uploadButton, BorderLayout.NORTH);

    mainPanel.add(postPanel, BorderLayout.SOUTH);

    recommendationTextArea = new JTextArea();
    recommendationTextArea.setEditable(false);
    JScrollPane recommendationScrollPane = new JScrollPane(recommendationTextArea);

    JPanel recommendationPanel = new JPanel(new BorderLayout());
    recommendationPanel.add(recommendationScrollPane, BorderLayout.CENTER);

    homePanel.add(mainPanel, BorderLayout.CENTER);
    homePanel.add(recommendationPanel, BorderLayout.EAST);

    revalidate();
    repaint();
  }

  private void saveImagePathToDB(String username, String imagePath) {
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
      String query = "INSERT INTO user_images (username, image_path) VALUES (?, ?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, imagePath);
        preparedStatement.executeUpdate();
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  private void displayImageInNewsFeed(String imagePath) {
    ImageIcon imageIcon = new ImageIcon(imagePath);
    Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    ImageIcon scaledIcon = new ImageIcon(image);

    JLabel imageLabel = new JLabel(scaledIcon);

    JTextPane textPane = new JTextPane();
    textPane.setEditable(false);

    StyledDocument doc = textPane.getStyledDocument();
    SimpleAttributeSet attrs = new SimpleAttributeSet();
    StyleConstants.setAlignment(attrs, StyleConstants.ALIGN_LEFT);

    try {
      doc.insertString(doc.getLength(), "\n", attrs);
      doc.insertComponent(imageLabel);
      doc.insertString(doc.getLength(), "\n\n", attrs);

      newsFeedTextArea.insertComponent(imageLabel);
      newsFeedTextArea.setCaretPosition(newsFeedTextArea.getDocument().getLength());
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
  }

  private boolean authenticateUser(String username, String password) {
    // Authenticates the user against the database
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
      String query = "SELECT * FROM users WHERE username=? AND password=?";
      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }

  private boolean registerUser(String username, String password) {
    // Registers a new user in the database
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
      if (isUsernameTaken(username)) {
        return false;
      }

      String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        int affectedRows = preparedStatement.executeUpdate();
        return affectedRows > 0;
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }

  private boolean isUsernameTaken(String username) {
    // Checks if a username is already taken in the database
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
      String query = "SELECT * FROM users WHERE username=?";
      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      return true;
    }
  }

  private boolean followUser(String username) {
    // Follows another user
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
      String query = "INSERT INTO user_followers (follower, following) VALUES (?, ?)";
      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, currentUser);
        preparedStatement.setString(2, username);
        int affectedRows = preparedStatement.executeUpdate();
        return affectedRows > 0;
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }

  private void viewUserProfile(String username) {
    // Views the profile of another user

    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
      String query = "SELECT * FROM users WHERE username=?";
      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
          String userProfile = "Username: " + resultSet.getString("username") + "\n"
              + "User ID: " + resultSet.getInt("user_id") + "\n"
              + "Other Profile Information: ..."; // Add other relevant profile information
          JOptionPane.showMessageDialog(null, userProfile, "User Profile - " + username,
              JOptionPane.INFORMATION_MESSAGE);
        } else {
          JOptionPane.showMessageDialog(null, "User not found", "Profile Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  private void recommendContent() {
    recommendationTextArea.append("Recommended Content:\n - Item 1\n - Item 2\n - Item 3\n");
  }

  public static void main(String[] args) {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new SocialMediaApp();
      }
    });
  }
}
