//Match AuthResponse interface from backend
//in this interface it will have data of generic type T, optional message and success boolean

export interface AuthRequest {
    username?: string; // Optional - only needed for register, not login
    email: string;
    password: string;
}

export interface AuthResponse {
    token: string;
    username: string;
    email: string;
    message : string;
}

//Match User interface from backend
export interface User {
    username: string;
    email: string;
}
