package src.q6;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class ImageDownloaderApp {

    private JFrame frame; // JFrame to hold the GUI components
    private JTextField urlTextField; // TextField for entering the image URL
    private JButton downloadButton; // Button to trigger image download
    private JTextArea logTextArea; // TextArea to display log messages
    private JPanel imagePanel; // Panel to display the downloaded image
    private JPanel progressPanel; // Panel to show progress indicators

    private ExecutorService executorService; // ExecutorService to manage thread pool
    private CompletionService<DownloadResult> completionService; // CompletionService to manage completed tasks
    private final ReentrantLock lock = new ReentrantLock(); // ReentrantLock to manage access to critical section

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { // Run GUI initialization on EDT
            try {
                new ImageDownloaderApp().initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Initialize the GUI components
    private void initialize() {
        frame = new JFrame("Image Downloader"); // Set up the JFrame
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Initialize GUI components
        urlTextField = new JTextField();
        downloadButton = new JButton("Download");
        logTextArea = new JTextArea();
        imagePanel = new JPanel();
        progressPanel = new JPanel();

        // Initialize ExecutorService with fixed thread pool of size 3
        executorService = Executors.newFixedThreadPool(3);
        // Initialize CompletionService
        completionService = new ExecutorCompletionService<>(executorService);

        // Add ActionListener to downloadButton
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imageUrl = urlTextField.getText();
                if (!imageUrl.isEmpty()) {
                    downloadImage(imageUrl); // Call method to start image download
                }
            }
        });

        // Add components to the frame
        frame.add(createInputPanel(), BorderLayout.NORTH);
        frame.add(createImagePanel(), BorderLayout.CENTER);
        frame.add(createLogPanel(), BorderLayout.SOUTH);

        frame.setVisible(true); // Make frame visible
    }

    // Create panel for input components
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel("Enter Image URL: "), BorderLayout.WEST);
        inputPanel.add(urlTextField, BorderLayout.CENTER);
        inputPanel.add(downloadButton, BorderLayout.EAST);
        return inputPanel;
    }

    // Create panel for image display
    private JPanel createImagePanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(imagePanel, BorderLayout.CENTER);
        mainPanel.add(progressPanel, BorderLayout.SOUTH);
        return mainPanel;
    }

    // Create panel for log display
    private JPanel createLogPanel() {
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.add(new JLabel("Log: "), BorderLayout.NORTH);
        logPanel.add(new JScrollPane(logTextArea), BorderLayout.CENTER);
        return logPanel;
    }

    // Method to initiate image download
    private void downloadImage(String imageUrl) {
        Callable<DownloadResult> downloadCallable = () -> {
            lock.lock(); // Acquire the lock before entering the critical section
            try {
                log("Downloading image: " + imageUrl); // Log download initiation
                BufferedImage image = loadImage(imageUrl); // Load image from URL

                // Simulate a time-consuming operation (e.g., image processing)
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(50);
                }

                saveImageToFile(image, imageUrl); // Save image to file
                log("Image downloaded successfully: " + imageUrl); // Log download success
                return new DownloadResult(image, imageUrl); // Return DownloadResult
            } finally {
                lock.unlock(); // Release the lock after exiting the critical section
            }
        };

        completionService.submit(downloadCallable); // Submit download task to CompletionService

        // Schedule a task to handle the completion of the download
        executorService.execute(() -> {
            try {
                Future<DownloadResult> future = completionService.take(); // Wait for download completion
                DownloadResult result = future.get(); // Get the DownloadResult
                SwingUtilities.invokeLater(() -> {
                    displayImage(result.getImage()); // Display downloaded image
                    log("Image downloaded successfully: " + result.getImageUrl()); // Log success
                    frame.revalidate(); // Revalidate frame to reflect changes
                    frame.repaint(); // Repaint frame to update UI
                });
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace(); // Handle exceptions
            }
        });
    }

    // Load image from URL
    private BufferedImage loadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            return ImageIO.read(url); // Read image from URL
        } catch (IOException e) {
            log("Error loading image: " + e.getMessage()); // Log errors
            return null;
        }
    }

    // Save image to file
    private void saveImageToFile(BufferedImage image, String imageUrl) {
        try {
            String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1); // Extract filename
            File outputFile = new File(fileName); // Create output file
            ImageIO.write(image, "jpg", outputFile); // Write image to file
            log("Image saved to file: " + outputFile.getAbsolutePath()); // Log success
        } catch (IOException e) {
            log("Error saving image to file: " + e.getMessage()); // Log errors
        }
    }

    // Display image on imagePanel
    private void displayImage(BufferedImage image) {
        imagePanel.removeAll(); // Clear previous image
        JLabel imageLabel = new JLabel(new ImageIcon(image)); // Create JLabel with image
        imagePanel.add(imageLabel); // Add imageLabel to imagePanel
    }

    // Log messages to logTextArea
    private void log(String message) {
        logTextArea.append(message + "\n"); // Append message to logTextArea
    }

    // Nested class to represent download result
    private static class DownloadResult {
        private BufferedImage image;
        private String imageUrl;

        public DownloadResult(BufferedImage image, String imageUrl) {
            this.image = image;
            this.imageUrl = imageUrl;
        }

        public BufferedImage getImage() {
            return image;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }
}


