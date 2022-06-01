import {
    IonAlert,
    IonButton,
    IonCardContent,
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
import {keyOutline} from 'ionicons/icons';
import React, {useCallback, useContext, useState} from 'react';
import {WebAPI} from '../../services/webAPI';
import '../Login.css';

const PasswordChangePage: React.FC = () => {

    const [password, setPassword] = useState<string>("");
    const [newPassword, setNewPassword] = useState<string>("");
    const [confirmPassword, setConfirmPassword] = useState<string>("");
    const [isError, setIsError] = useState<boolean>(false);
    const [ok, setOk] = useState<boolean>(false);
    const [message, setMessage] = useState<string>("");

    const {navigate} = useContext(NavContext);
    const goBack = useCallback(() => navigate("/profile", "back", "replace"), [navigate]);
    const goToLogin = useCallback(() => navigate("/login", "forward", "replace"), [navigate]);


    const doChange = async () => {
        if (!password) {
            setMessage("Aktuális jelszó megadása kötelező");
            setIsError(true);
            return;
        }
        if (!newPassword) {
            setMessage("Új jelszó kitöltése kötelező");
            setIsError(true);
            return;
        }

        if (!confirmPassword) {
            setMessage("Megerősítő jelszó kitöltése kötelező");
            setIsError(true);
            return;
        }
        if (newPassword !== confirmPassword) {
            setMessage("Az új jelszó és a megerősítés nem egyezik");
            setIsError(true);
            return;
        }

        const userId = WebAPI.getLoggedUser()!.user_id.toString();
        const result = await WebAPI.userService.changePassword(
            {
                user_id: userId,
                new_password: newPassword,
                old_password: password
            }
        );
        if (result !== true) {
            setMessage("Hiba a jelszó megváltoztatása közben <br/><br/> Message: " + result);
            setIsError(true);
            return;
        }
        setMessage("A jelszót sikeresen lecseréltük!");
        setOk(true);
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
                                buttons={["OK"]}
                            />
                            <IonAlert
                                isOpen={ok}
                                onDidDismiss={() => {
                                    setOk(false);
                                    localStorage.clear();
                                    goToLogin();
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
                                icon={keyOutline}
                            />
                        </IonCol>
                    </IonRow>
                    <IonRow>
                        <IonCol>
                            <IonItem>
                                <IonLabel position="floating">Aktuális jelszó</IonLabel>
                                <IonInput
                                    placeholder="***************"
                                    type="password"
                                    value={password}
                                    onIonChange={e => setPassword(e.detail.value!)}
                                >
                                </IonInput>
                            </IonItem>
                        </IonCol>
                    </IonRow>

                    <IonRow>
                        <IonCol>
                            <IonItem>
                                <IonLabel position="floating">Új jelszó</IonLabel>
                                <IonInput
                                    placeholder="***************"
                                    type="password"
                                    value={newPassword}
                                    onIonChange={(e) => setNewPassword(e.detail.value!)}
                                >
                                </IonInput>
                            </IonItem>
                        </IonCol>
                    </IonRow>
                    <IonRow>
                        <IonCol>
                            <IonItem>
                                <IonLabel position="floating"> Megerősítő jelszó</IonLabel>
                                <IonInput
                                    placeholder="***************"
                                    type="password"
                                    value={confirmPassword}
                                    onIonChange={(e) => setConfirmPassword(e.detail.value!)}
                                >
                                </IonInput>
                            </IonItem>
                        </IonCol>
                    </IonRow>
                </IonGrid>

                <IonCardContent>
                    <IonRow/>
                    <IonRow>
                        <IonCol>
                            <IonButton fill="outline" onClick={() => goBack()} expand="block">
                                <p>VISSZA</p>
                            </IonButton>
                        </IonCol>
                        <IonCol>
                            <IonButton fill="outline" expand="block" onClick={() => doChange()}>
                                <p>CSERE</p>
                            </IonButton>
                        </IonCol>
                    </IonRow>
                </IonCardContent>
            </IonContent>
        </IonPage>
    );
};

export default PasswordChangePage;