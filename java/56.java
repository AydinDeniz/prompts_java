// Certificate.java
import java.time.LocalDate;

public class Certificate {
    private String id;
    private String owner;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private boolean isRevoked;

    public Certificate(String id, String owner, LocalDate issueDate, LocalDate expiryDate) {
        this.id = id;
        this.owner = owner;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.isRevoked = false;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public boolean isRevoked() {
        return isRevoked;
    }

    public void setRevoked(boolean revoked) {
        isRevoked = revoked;
    }

    @Override
    public String toString() {
        return "Certificate[ID: " + id + ", Owner: " + owner + ", Issue Date: " + issueDate + ", Expiry Date: " + expiryDate + ", Revoked: " + isRevoked + "]";
    }
}

// CertificateAuthority.java
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CertificateAuthority {
    private Map<String, Certificate> certificates;

    public CertificateAuthority() {
        this.certificates = new HashMap<>();
    }

    public Certificate issueCertificate(String owner) {
        String id = UUID.randomUUID().toString();
        LocalDate issueDate = LocalDate.now();
        LocalDate expiryDate = issueDate.plusYears(1);
        Certificate certificate = new Certificate(id, owner, issueDate, expiryDate);
        certificates.put(id, certificate);
        System.out.println("Issued " + certificate);
        return certificate;
    }

    public Certificate renewCertificate(String id) {
        Certificate cert = certificates.get(id);
        if (cert != null && !cert.isRevoked()) {
            LocalDate newExpiryDate = cert.getExpiryDate().plusYears(1);
            Certificate renewedCert = new Certificate(id, cert.getOwner(), cert.getIssueDate(), newExpiryDate);
            certificates.put(id, renewedCert);
            System.out.println("Renewed " + renewedCert);
            return renewedCert;
        } else {
            System.out.println("Certificate not found or revoked for ID: " + id);
            return null;
        }
    }

    public void revokeCertificate(String id) {
        Certificate cert = certificates.get(id);
        if (cert != null) {
            cert.setRevoked(true);
            System.out.println("Revoked " + cert);
        } else {
            System.out.println("Certificate not found for ID: " + id);
        }
    }

    public Certificate getCertificate(String id) {
        return certificates.get(id);
    }
}

// CertificateManager.java
import java.util.Scanner;

public class CertificateManager {
    public static void main(String[] args) {
        CertificateAuthority authority = new CertificateAuthority();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nCertificate Management System:");
            System.out.println("1. Issue Certificate");
            System.out.println("2. Renew Certificate");
            System.out.println("3. Revoke Certificate");
            System.out.println("4. View Certificate");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter owner name: ");
                    String owner = scanner.nextLine();
                    authority.issueCertificate(owner);
                    break;
                case 2:
                    System.out.print("Enter certificate ID to renew: ");
                    String renewId = scanner.nextLine();
                    authority.renewCertificate(renewId);
                    break;
                case 3:
                    System.out.print("Enter certificate ID to revoke: ");
                    String revokeId = scanner.nextLine();
                    authority.revokeCertificate(revokeId);
                    break;
                case 4:
                    System.out.print("Enter certificate ID to view: ");
                    String viewId = scanner.nextLine();
                    Certificate cert = authority.getCertificate(viewId);
                    if (cert != null) {
                        System.out.println("Certificate Details: " + cert);
                    } else {
                        System.out.println("Certificate not found for ID: " + viewId);
                    }
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}