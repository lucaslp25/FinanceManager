export interface TransactionResponseDTO{
    id: number;
    transactionType: 'DEPOSIT' | 'WITHDRAW';
    amount: number;
    newBalance: number;
    message: string;
    userId: number;
}

export interface WithdrawDTO{
    amount: number;
    categoryId: number;
    description?: string;
}

export interface WithdrawTransactionResponseDTO{
    transactionId: string;
    date: Date;
    amount: number;
    categoryId: number;
    categoryName: string;
    description: string;
}

export interface WithdrawTransactionEditDTO{
    amount: number | null;
    description: string | null;
    categoryId: number | null;
    date: Date | string | null;
}