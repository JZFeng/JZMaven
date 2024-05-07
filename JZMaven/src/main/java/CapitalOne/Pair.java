package CapitalOne;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair {
    String accountId;
    double transactionSum;
}
