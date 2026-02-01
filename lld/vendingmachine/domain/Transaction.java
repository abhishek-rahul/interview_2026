package lld.vendingmachine.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Transaction {
    private String selectedCode; // null if none
    private final List<Integer> insertedDenoms; // exact trail for refund
    private int insertedAmount;

    public Transaction() {
        this.selectedCode = null;
        this.insertedDenoms = new ArrayList<>();
        this.insertedAmount = 0;
    }

    public String selectedCode() {
        return selectedCode;
    }

    public List<Integer> insertedDenoms() {
        return insertedDenoms;
    }

    public int totalInserted() {
        return insertedAmount;
    }

    public void select(String code) {
        // new selection starts a fresh transaction
        reset();
        this.selectedCode = code;
    }

    public void addMoney(int denom) {
        this.insertedDenoms.add(denom);
        this.insertedAmount += denom;
    }

    public List<Integer> refundAllAndReset() {
        List<Integer> refund = new ArrayList<>(this.insertedDenoms);
        reset();
        return refund;
    }

    public void reset() {
        this.selectedCode = null;
        this.insertedDenoms.clear();
        this.insertedAmount = 0;
    }
}
