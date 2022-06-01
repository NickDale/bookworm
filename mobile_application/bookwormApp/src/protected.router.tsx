import React, {useContext, useEffect} from "react"
import {Redirect, Route} from "react-router";
import {AuthContext} from "./context";
import {WebAPI} from "./services/webAPI";

interface ProtectedRouteProps {
    component: React.ComponentType<any>;
    path: string;
}

export const ProtectedRoute: React.FC<ProtectedRouteProps> = ({component: Component, path}) => {
    const context = useContext(AuthContext);

    useEffect(() => {
    }, [Component, path]);

    const hasToken = (): boolean => {
        const token = WebAPI.storageService.getToken();
        return (token !== null && token !== "");
    }

    context.isAuthenticated = hasToken();
    return (
        <Route path={path}
               render={props => context.isAuthenticated ? <Component {...props} /> : <Redirect to="/login"/>}/>
    );
};