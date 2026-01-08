import { createContext, useContext,useState,useEffect, ReactNode } from "react";
import { authService } from "../services/authService";
import { AuthResponse, AuthRequest, User } from "../types/auth";

//define AuthContextType interface
interface AuthContextType {
    user: User | null;
    isAuthenticated: boolean;
    loading: boolean;
    login: (data : AuthRequest) => Promise<AuthResponse>;
    register: (data : AuthRequest) => Promise<AuthResponse>;
    logout: () => void;
}

//create AuthContext
const AuthContext = createContext<AuthContextType | undefined>(undefined);

//AuthContextProvider component
export const AuthContextProvider = ({children} : {children : ReactNode}) => {
    const [user, setUser] = useState<User | null>(null);
    const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        const token = authService.getToken();
        const savedUser = localStorage.getItem("user");
        if (token && savedUser) {
            setUser(JSON.parse(savedUser));
            setIsAuthenticated(true);
        } else {
            setIsAuthenticated(false);
        }
        setLoading(false);
    }, []);

    const login = async (data : AuthRequest) => {
        const response = await authService.login(data);
        
        //Save token and user to localStorage
        localStorage.setItem("token", response.token);
        const userData = {username : response.username, email : response.email};
        localStorage.setItem("user", JSON.stringify(userData));

        //Update state
        setUser(userData);
        setIsAuthenticated(true);
        return response;
    };

    const register = async (data : AuthRequest) => {
        const response = await authService.register(data);
        
        //Save token and user to localStorage
        localStorage.setItem("token", response.token);
        const userData = {username : response.username, email : response.email};
        localStorage.setItem("user", JSON.stringify(userData));

        //Update state
        setUser(userData);
        setIsAuthenticated(true);
        return response;
    };

    const logout = () => {
        authService.logout();
        
        //Remove token and user from localStorage
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        
        //Update state
        setUser(null);
        setIsAuthenticated(false);
    };

    return (
        <AuthContext.Provider value=
        {{
            user, 
            isAuthenticated, 
            loading, 
            login, 
            register, 
            logout
        }}>
            {children}
        </AuthContext.Provider>
    );
};

//useAuth hook
export function useAuth () {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within an AuthContextProvider");
    }
    return context;
}
