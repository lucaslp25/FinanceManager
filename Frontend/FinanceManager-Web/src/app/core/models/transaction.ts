export interface TransactionResponseDTO{
    transactionId: string;
    date: Date;
    categoryName: string;
    transactionType: 'DEPOSIT' | 'WITHDRAW';
    amount: number;
    newBalance: number;
    description: string;
    userId: number;
    categoryId: number;
}

export interface WithdrawDTO{
    amount: number;
    categoryId: number;
    description?: string;
}

export interface BalanceDTO{
    amount: number;
    categoryId: number;
    description?: string;
}

export interface TransactionEditDTO{
    amount: number | null;
    description: string | null;
    categoryId: number | null;
    date: Date | string | null;
    transactionType: 'DEPOSIT' | 'WITHDRAW';
}