package CapitalOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Activity {
    private String userId;
    private Date date;
    private String description;
    private TransactionType  transactionType;
    private double amount;
    private double availableBalance;



}
