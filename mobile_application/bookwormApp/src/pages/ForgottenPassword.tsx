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
    IonTitle,
    IonToolbar,
    NavContext
} from '@ionic/react';
import {personCircle} from 'ionicons/icons';
import React, {useCallback, useContext, useState} from 'react';
import {WebAPI} from '../services/webAPI';

const ForgottenPasswordPage: React.FC = () => {

    const [email, setEmail] = useState<string>("");
    const [iserror, setIserror] = useState<boolean>(false);
    const [isOk, setOk] = useState<boolean>(false);
    const [message, setMessage] = useState<string>("");

    const {navigate} = useContext(NavContext);
    const goToLoginPage = useCallback(() => navigate("/", "back", "replace"), [navigate]);

    const resetPassword = async () => {
        if (!email) {
            setMessage("Please enter a valid email");
            setIserror(true);
            return;
        }

        let result = await WebAPI.loginService.passwordReset(email);
        if (result instanceof Error || !result) {
            setMessage("Nem megfelelő email vagy fehasználónév");
            setIserror(true);
            return;
        }
        setMessage("Az új jelszót elküldtük az email címére");
        setOk(true);
    }

    return (
        <IonPage>
            <IonHeader>
                <IonToolbar>
                    <IonTitle>Elefelejtett jelszó</IonTitle>
                </IonToolbar>
            </IonHeader>
            <IonContent fullscreen className="ion-padding ion-text-center">
                <IonGrid>
                    <IonRow>
                        <IonCol>
                            <IonAlert
                                isOpen={iserror}
                                onDidDismiss={() => setIserror(false)}
                                cssClass="my-custom-class"
                                header={"Error!"}
                                message={message}
                                buttons={["OK"]}
                            />
                            <IonAlert
                                isOpen={isOk}
                                onDidDismiss={() => {
                                    setOk(false);
                                    goToLoginPage();
                                }}
                                cssClass="my-custom-class"
                                header={"Elfelejtett jelszó"}
                                message={message}
                                buttons={["OK"]}
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
                                    type="text"
                                    value={email}
                                    onIonChange={e => setEmail(e.detail.value!)}
                                >
                                </IonInput>
                            </IonItem>
                        </IonCol>
                    </IonRow>
                </IonGrid>
                <IonButton expand="block" onClick={() => resetPassword()}>Küldés</IonButton>
            </IonContent>
        </IonPage>
    );
};

export default ForgottenPasswordPage;