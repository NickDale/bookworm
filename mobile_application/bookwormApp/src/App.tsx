import {IonApp, IonRouterOutlet, IonSplitPane, setupIonicReact} from '@ionic/react';
import {IonReactRouter} from '@ionic/react-router';
import {Redirect, Route} from 'react-router-dom';
import Menu from './components/Menu';

/* Core CSS required for Ionic components to work properly */
import '@ionic/react/css/core.css';

/* Basic CSS for apps built with Ionic */
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';

/* Optional CSS utils that can be commented out */
import '@ionic/react/css/padding.css';
import '@ionic/react/css/float-elements.css';
import '@ionic/react/css/text-alignment.css';
import '@ionic/react/css/text-transformation.css';
import '@ionic/react/css/flex-utils.css';
import '@ionic/react/css/display.css';

/* Theme variables */
import './theme/variables.css';
import BookPage from './pages/books/Books';
import ContactPage from './pages/contact/Contact';
import ProfilePage from './pages/profile/Profile';
import LoanPage from './pages/loans/Loans';
import LoginPage from './pages/Login';
import React from 'react';
import BookDetailPage from './pages/books/BookDetail';
import ForgottenPasswordPage from './pages/ForgottenPassword';
import PasswordChangePage from './pages/profile/PasswordChange';
import {ProtectedRoute} from './protected.router';
import ModifyPersonalDetailPage from "./pages/profile/ModifyPersonalDetails";

setupIonicReact();

const App: React.FC = () => {
    return (
        <IonApp>
            <IonReactRouter>
                <IonSplitPane contentId="main">
                    <Menu/>
                    <IonRouterOutlet id="main">
                        <Route path="/" exact={true}>
                            <Redirect to="/login"/>
                        </Route>
                        <Route path="/login" exact={true}>
                            <LoginPage/>
                        </Route>
                        <Route path="/forgotten/password" exact={true}>
                            <ForgottenPasswordPage/>
                        </Route>
                        <ProtectedRoute path="/profile" component={ProfilePage}/>
                        <ProtectedRoute path="/modify" component={ModifyPersonalDetailPage}/>
                        <ProtectedRoute path="/password/change" component={PasswordChangePage}/>
                        <ProtectedRoute path="/books" component={BookPage}/>
                        <ProtectedRoute path="/contact" component={ContactPage}/>
                        <ProtectedRoute path="/loans" component={LoanPage}/>
                        <ProtectedRoute path="/history" component={LoanPage}/>
                        <ProtectedRoute path="/book/detail/:id" component={BookDetailPage}/>
                    </IonRouterOutlet>
                </IonSplitPane>
            </IonReactRouter>
        </IonApp>
    );
};

export default App;
