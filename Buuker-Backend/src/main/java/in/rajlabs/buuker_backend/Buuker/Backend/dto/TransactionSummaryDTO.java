package in.rajlabs.buuker_backend.Buuker.Backend.dto;

public class TransactionSummaryDTO {
    private String customerId;
    private Long totalTransactions;
    private Double totalAmount;

    // Constructors
    public TransactionSummaryDTO() {}

    public TransactionSummaryDTO(String customerId, Long totalTransactions, Double totalAmount) {
        this.customerId = customerId;
        this.totalTransactions = totalTransactions;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(Long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
