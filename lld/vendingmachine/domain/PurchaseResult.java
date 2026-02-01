package lld.vendingmachine.domain;

import java.util.List;

import lld.vendingmachine.domain.enums.PurchaseStatus;


public class PurchaseResult {
    private final PurchaseStatus status;
    private final String dispensedItemCode;   // null on failure
    private final List<Integer> changeDenoms; // empty if none
    private final String message;

    public PurchaseResult(PurchaseStatus status, String dispensedItemCode, List<Integer> changeDenoms, String message) {
        this.status = status;
        this.dispensedItemCode = dispensedItemCode;
        this.changeDenoms = changeDenoms;
        this.message = message;
    }

    public PurchaseStatus status() { return status; }
    public String dispensedItemCode() { return dispensedItemCode; }
    public List<Integer> changeDenoms() { return changeDenoms; }
    public String message() { return message; }

    @Override
    public String toString() {
        return "PurchaseResult{" +
                "status=" + status +
                ", dispensedItemCode='" + dispensedItemCode + '\'' +
                ", changeDenoms=" + changeDenoms +
                ", message='" + message + '\'' +
                '}';
    }
}
