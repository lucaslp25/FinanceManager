export interface LoginDTO {
    email?: string;
    password?: string;
}

export interface LoginResponseDTO {
    firstName?: string;
    lastName?: string;
    email?: string;
    role?: 'USER' | 'ADMIN';
    token?: string;
}

export interface RegisterDTO {
    firstName?: string;
    lastName?: string;
    username?: string;
    email?: string;
    password?: string;
}

export interface RegisterResponseDTO {
    firstName?: string;
    lastName?: string;
    email?: string;
    role?: 'USER' | 'ADMIN';
}
