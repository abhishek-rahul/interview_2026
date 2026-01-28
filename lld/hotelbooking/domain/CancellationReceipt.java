package lld.hotelbooking.domain;

public class CancellationReceipt {
    private final String bookingId;
    private final long refundAmount;
    private final long feeAmount;

    public CancellationReceipt(String bookingId, long refundAmount, long feeAmount) {
        this.bookingId = bookingId;
        this.refundAmount = refundAmount;
        this.feeAmount = feeAmount;
    }

    public String getBookingId() { return bookingId; }
    public long getRefundAmount() { return refundAmount; }
    public long getFeeAmount() { return feeAmount; }
}
