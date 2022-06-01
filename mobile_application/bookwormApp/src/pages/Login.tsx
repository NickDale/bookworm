import {
    IonAlert,
    IonButton,
    IonCol,
    IonContent,
    IonGrid,
    IonHeader,
    IonIcon,
    IonInput,
    IonItem,
    IonLabel,
    IonPage,
    IonRow,
    IonToolbar,
    NavContext
} from '@ionic/react';
import {personCircle} from 'ionicons/icons';
import React, {useCallback, useContext, useState} from 'react';
import {WebAPI} from '../services/webAPI';
import './Login.css';

const LoginPage: React.FC = () => {

    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [isError, setIsError] = useState<boolean>(false);
    const [message, setMessage] = useState<string>("");

    const {navigate} = useContext(NavContext);
    const goToForgottenPasswordPage = useCallback(() => navigate("/forgotten/password", "forward", "replace"), [navigate]);
    const goToProfilePage = useCallback(() => navigate("/profile/", "forward", "push"), [navigate]);

    const doLogin = async () => {
        if (!email) {
            setMessage("Événytelen email vagy felhasználónév");
            setIsError(true);
            return;
        }
        if (!password) {
            setMessage("Jelszó kitöltése kötelező");
            setIsError(true);
            return;
        }

        let result = await WebAPI.loginService.login({username: email, password: password});
        if (!result) {
            setMessage("Hibás bejelentkezési adatok");
            setIsError(true);
            return;
        } else {
            goToProfilePage();
        }
    }

    return (
        <IonPage>
            <IonHeader>
                <IonToolbar/>
            </IonHeader>
            <IonContent fullscreen className="ion-padding ion-text-center">
                <IonGrid>

                    <IonRow>
                        <IonCol>
                            <IonAlert
                                isOpen={isError}
                                onDidDismiss={() => setIsError(false)}
                                cssClass="my-custom-class"
                                header={"Error!"}
                                message={message}
                                buttons={["Dismiss"]}
                            />
                        </IonCol>
                    </IonRow>

                    <IonRow>
                        <IonCol>
                            <IonIcon
                                style={{fontSize: "70px", color: "#0040ff"}}
                                icon={personCircle}
                            />
                        </IonCol>
                    </IonRow>

                    <IonRow>
                        <IonCol>
                            <IonItem>
                                <IonLabel position="floating"> Email / Felhasználónév</IonLabel>
                                <IonInput
                                    placeholder="kis.pista@gmail.com"
                                    type="email"
                                    value={email}
                                    onIonChange={e => setEmail(e.detail.value!)}
                                >
                                </IonInput>
                            </IonItem>
                        </IonCol>
                    </IonRow>

                    <IonRow>
                        <IonCol>
                            <IonItem>
                                <IonLabel position="floating"> Jelszó</IonLabel>
                                <IonInput
                                    placeholder="***************"
                                    type="password"
                                    value={password}
                                    onIonChange={(e) => setPassword(e.detail.value!)}
                                >
                                </IonInput>
                            </IonItem>
                        </IonCol>
                    </IonRow>

                </IonGrid>
                <IonButton expand="block" onClick={() => doLogin()}>Bejelentkezés</IonButton>

                <p style={{fontSize: "medium"}}>
                    <a onClick={e => goToForgottenPasswordPage()}>Elfelejtettem a jelszavam</a>
                </p>
            </IonContent>
        </IonPage>
    );
};

export default LoginPage;