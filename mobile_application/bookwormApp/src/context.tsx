import { createContext, Dispatch } from "react";

interface AuthDefaultContext {
    isAuthenticated: boolean;
    dispatch: Dispatch<any>
}

export const AuthContext = createContext<AuthDefaultContext>({
    isAuthenticated: false,
    dispatch: () => { }
});
