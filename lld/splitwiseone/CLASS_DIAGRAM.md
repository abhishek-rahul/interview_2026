# Splitwise Service – Simplified Class Diagram

This version removes these separate classes:

- `SplitInput`
- `SplitLine`
- `TransferSuggestion`

Instead it uses simple maps:

- Split input: `Map<String, Long>`
- Expense split lines: `Map<String, Long>` inside `Expense`
- Simplified transfers: `Map<String, Map<String, Long>>`

---

```mermaid
classDiagram
    direction TB

    class MainDemo {
        +main(String[] args)$
        -money(long paise)$
        -printBalances(String, Map, Map)$
        -printSimplified(String, Map, Map)$
    }

    class SplitwiseService {
        -SplitwiseCatalog catalog
        -SplitPolicyFactory splitPolicyFactory
        -DebtSimplificationPolicyFactory debtPolicyFactory
        +createUser(String name) String
        +createGroup(String name, List~String~ memberUserIds, DebtSimplifyMode mode) String
        +addExpense(groupId, payerId, totalPaise, SplitType, Map~String,Long~ splitValues, note) String
        +getBalances(String groupId) Map~String,Long~
        +getSimplifiedDebts(String groupId) Map~String,Map~String,Long~~
        +settleUp(groupId, fromUserId, toUserId, amountPaise) String
        +splitValues(Object... pairs)$ Map~String,Long~
    }

    class SplitwiseCatalog {
        +addUser(User u)
        +addGroup(Group g)
        +findUserById(String userId) User
        +findGroupById(String groupId) Group
    }

    class User {
        +String id
        +String name
    }

    class Group {
        +String id
        +String name
        +List~String~ memberUserIds
        +List~Expense~ expenses
        +List~Settlement~ settlements
        +Ledger ledger
        -DebtSimplifyMode simplifyMode
        +hasMember(String userId) boolean
        +addMember(String userId)
        +addExpense(Expense e)
        +addSettlement(Settlement s)
    }

    class Ledger {
        -Map~String,Long~ netBalance
        +applyExpense(Expense e)
        applySettlement(Settlement s)
        +getNetPaise(String userId) long
        +snapshot() Map~String,Long~
    }

    class Expense {
        +String id
        +String groupId
        +String payerId
        +long totalPaise
        +Map~String,Long~ owedByUser
        +String note
        +long createdAtEpochMs
    }

    class Settlement {
        +String id
        +String groupId
        +String fromUserId
        +String toUserId
        +long amountPaise
        +long createdAtEpochMs
    }

    class SplitPolicy {
        <<interface>>
        +validate(long, String, Map~String,Long~)
        +compute(long, String, Map~String,Long~) Map~String,Long~
    }

    class DebtSimplificationPolicy {
        <<interface>>
        +simplify(Map~String,Long~) Map~String,Map~String,Long~~
    }

    class EqualSplitPolicy
    class ExactSplitPolicy
    class PercentSplitPolicy
    class ShareSplitPolicy
    class GreedyDebtSimplificationPolicy
    class MinTransfersDebtSimplificationPolicy
    class SplitPolicyFactory
    class DebtSimplificationPolicyFactory

    class SplitType {
        <<enumeration>>
        EQUAL
        EXACT
        PERCENT
        SHARE
    }

    class DebtSimplifyMode {
        <<enumeration>>
        GREEDY_FAST
        MIN_TRANSFERS
    }

    MainDemo ..> SplitwiseService : uses
    SplitwiseService --> SplitwiseCatalog : owns
    SplitwiseService --> SplitPolicyFactory : owns
    SplitwiseService --> DebtSimplificationPolicyFactory : owns
    SplitwiseService ..> Expense : creates
    SplitwiseService ..> Settlement : creates
    SplitwiseService ..> SplitPolicy : uses
    SplitwiseService ..> DebtSimplificationPolicy : uses

    SplitwiseCatalog "1" *-- "0..*" User
    SplitwiseCatalog "1" *-- "0..*" Group

    Group "1" *-- "1" Ledger
    Group "1" *-- "0..*" Expense
    Group "1" *-- "0..*" Settlement

    Ledger ..> Expense : reads owedByUser map
    Ledger ..> Settlement : applies payment

    EqualSplitPolicy --|> SplitPolicy
    ExactSplitPolicy --|> SplitPolicy
    PercentSplitPolicy --|> SplitPolicy
    ShareSplitPolicy --|> SplitPolicy

    GreedyDebtSimplificationPolicy --|> DebtSimplificationPolicy
    MinTransfersDebtSimplificationPolicy --|> DebtSimplificationPolicy

    SplitPolicyFactory ..> SplitPolicy : returns
    DebtSimplificationPolicyFactory ..> DebtSimplificationPolicy : returns
```
