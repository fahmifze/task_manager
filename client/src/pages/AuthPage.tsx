import { useState } from "react";
import { LoginForm } from "../components/LoginForm";
import { RegisterForm } from "../components/RegisterForm";

export function AuthPage() {
    const [isLogin, setIsLogin] = useState(true);
    
    return (
        <div className="flex justify-center items-center h-screen">
            <div className="w-full max-w-md">
                <div className="text-center mb-6">
                    <h2 className="text-2xl font-bold mb-2">{isLogin ? "Login" : "Register"}</h2>
                    <button
                        onClick={() => setIsLogin(!isLogin)}
                        className="text-blue-500 hover:underline"
                    >
                        {isLogin ? "Don't have an account?" : "Already have an account?"}
                    </button>
                </div>
                {isLogin ? <LoginForm /> : <RegisterForm />}
            </div>
        </div>
    );
}
