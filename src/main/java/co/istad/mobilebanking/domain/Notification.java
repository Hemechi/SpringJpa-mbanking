package co.istad.mobilebanking.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalTime transactionAt;

    @ManyToOne
    @JoinColumn(name="transaction_id")
    private Transaction transactionId;

    @ManyToOne
    @JoinColumn(name="sender_id")
    private User senderId;

    @ManyToOne
    @JoinColumn(name="receiver_id")
    private User receiverId;

}
