import { AuthRequest, AuthResponse } from "../types/auth"; // Import AuthRequest and AuthResponse types

const API_URL = "http://localhost:8080/api"; // Base URL for API requests

export const authService = {

    //Register
    register: async (authRequest: AuthRequest): Promise<AuthResponse> => {
        //FETCH post request to register endpoint
        const response = await fetch(`${API_URL}/auth/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(authRequest),
        });
        //Return response as JSON
        return response.json();
    },

    //Login
    login: async (authRequest: AuthRequest): Promise<AuthResponse> => {
        //FETCH post request to login endpoint
        const response = await fetch(`${API_URL}/auth/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(authRequest),
        });

        const data = await response.json();
        // save token to localStorage automatically
        if (data.token) {
            localStorage.setItem("token", data.token);
        }
        //Return response as JSON
        return data;
    },

    //Logout
    logout: async () => {
        //Remove token from localStorage
        localStorage.removeItem("token");
    },

    //Get token
    getToken: () : string | null => {
        //Return token from localStorage
        return localStorage.getItem("token");
    },
    
    //Check if user is authenticated
    isAuthenticated: () : boolean => {
        //Return true if token exists in localStorage
        return !!localStorage.getItem("token");
    },

    
};