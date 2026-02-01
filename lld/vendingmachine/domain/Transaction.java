package lld.vendingmachine.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Transaction {
    private String selectedCode;                 // null if none
    private final List<Integer> insertedDenoms;  // exact trail for refund
    private int insertedAmount;

    public Transaction() {
        this.selectedCode = null;
        this.insertedDenoms = new ArrayList<>();
        this.insertedAmount = 0;
    }

    public String selectedCode() { return selectedCode; }
    public List<Integer> insertedDenoms() { return insertedDenoms; }
    public int totalInserted() { return insertedAmount; }

    public void select(String code) {
        // no logic in Stage 6
    }

    public void addMoney(int denom) {
        // no logic in Stage 6
    }

    public List<Integer> refundAllAndReset() {
        return Collections.emptyList();
    }

    public void reset() {
        // no logic in Stage 6
    }
}
