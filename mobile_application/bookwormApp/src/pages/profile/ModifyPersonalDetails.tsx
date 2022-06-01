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
    IonLoading,
    IonPage,
    IonRow,
    IonToolbar,
    NavContext
} from '@ionic/react';
import {personOutline} from 'ionicons/icons';
import React, {useCallback, useContext, useEffect, useState} from 'react';
import {WebAPI} from '../../services/webAPI';
import '../Login.css';
import {LoggedUser} from "../../client/login";

const ModifyPersonalDetailPage: React.FC = () => {

    const [firstname, setFirstname] = useState<string>("");
    const [lastname, setLastname] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [phone, setPhone] = useState<string>("");
    const [isError, setIsError] = useState<boolean>(false);
    const [ok, setOk] = useState<boolean>(false);
    const [isLoading, setLoading] = useState<boolean>(true);
    const [message, setMessage] = useState<string>("");

    const {navigate} = useContext(NavContext);
    const goBack = useCallback(() => navigate("/profile", "back", "replace"), [navigate]);
    const goToProfilePage = useCallback(() => navigate("/profile", "forward", "replace"), [navigate]);

    useEffect(() => {
        setTimeout(() => {
            fetchData();
            setLoading(false);
        }, 1000)
    }, []);

    const fetchData = async (): Promise<void> => {
        const user: LoggedUser = await WebAPI.getLoggedUser();
        setLastname(user.lastname);
        setFirstname(user.firstname);
        setEmail(user.email);
        setPhone(user.phone);
    }

    const doChange = async () => {
        if (!email || email === "") {
            setMessage("Az email cím nem lehet üres");
            setIsError(true);
            return;
        }
        const isValidEmail = await WebAPI.userService.checkEmail(email);
        if (!isValidEmail) {
            setMessage("Ez az e-mail cím már foglalt");
            setIsError(true);
            return;
        }
        WebAPI.userService.updateProfile(
            {
                email: email,
                phone: phone,
                firstname: firstname,
                lastname: lastname
            }
        ).then(() => {
            setMessage("Az Adatokat sikeresen frissítettük!");
            setOk(true);
        });
    }

    return (
        <IonPage>
            <IonHeader>
                <IonToolbar/>
            </IonHeader>
            <IonLoading
                cssClass='loading'
                isOpen={isLoading}
                onDidDismiss={() => setLoading(false)}
                message={'Kérjük várjon...'}
                duration={5000}
            />
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
                                    goToProfilePage();
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
                                icon={personOutline}
                            />
                        </IonCol>
                    </IonRow>
                    <IonRow>
                        <IonCol>
                            <IonItem>
                                <IonLabel position="floating">Vezetéknév</IonLabel>
                                <IonInput
                                    type="text"
                                    value={lastname}
                                    onIonChange={e => setLastname(e.detail.value!)}
                                >
                                </IonInput>
                            </IonItem>
                        </IonCol>
                    </IonRow>

                    <IonRow>
                        <IonCol>
                            <IonItem>
                                <IonLabel position="floating">Keresztnév</IonLabel>
                                <IonInput
                                    type="text"
                                    value={firstname}
                                    onIonChange={(e) => setFirstname(e.detail.value!)}
                                >
                                </IonInput>
                            </IonItem>
                        </IonCol>
                    </IonRow>
                    <IonRow>
                        <IonCol>
                            <IonItem>
                                <IonLabel position="floating">E-mail</IonLabel>
                                <IonInput
                                    type="text"
                                    value={email}
                                    onIonChange={(e) => setEmail(e.detail.value!)}
                                >
                                </IonInput>
                            </IonItem>
                        </IonCol>
                    </IonRow>
                    <IonRow>
                        <IonCol>
                            <IonItem>
                                <IonLabel position="floating">Telefonszám</IonLabel>
                                <IonInput
                                    type="text"
                                    value={phone}
                                    onIonChange={(e) => setPhone(e.detail.value!)}
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
                                <p>MENTÉS</p>
                            </IonButton>
                        </IonCol>
                    </IonRow>
                </IonCardContent>
            </IonContent>
        </IonPage>
    );
};

export default ModifyPersonalDetailPage;