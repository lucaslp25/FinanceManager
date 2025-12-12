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