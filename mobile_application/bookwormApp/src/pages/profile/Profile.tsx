import {
    IonAlert,
    IonButton,
    IonButtons,
    IonCard,
    IonCardContent,
    IonCardHeader,
    IonCardSubtitle,
    IonCol,
    IonContent,
    IonGrid,
    IonHeader,
    IonIcon,
    IonLoading,
    IonMenuButton,
    IonPage,
    IonRow,
    IonText,
    IonTitle,
    IonToolbar,
} from '@ionic/react';
import './Profile.scss';
import './Profile.style.css';
import React, {useEffect, useState} from 'react';
import {businessOutline, callOutline, mailOutline} from 'ionicons/icons';
import {User} from '../../client/user';
import {WebAPI} from '../../services/webAPI';
import ImageSelectorComponent from "../../components/ImageSelectroComponent";

const ProfilePage: React.FC = () => {
    const [actualUser, setActualUser] = useState<User>();
    const [profileImg, setProfileImg] = useState<string>();
    const [isLoading, setLoading] = useState<boolean>(true);
    const [qrCode, setQrCode] = useState<boolean>(false);

    useEffect(() => {
        setTimeout(() => {
            fetchData();
            setLoading(false);
        }, 1000)
    }, []);

    const fetchData = async (): Promise<void> => {
        const data: User | undefined = await WebAPI.userService.getProfileDetails();
        setActualUser(data);
        WebAPI.setLoggedUser({
            user_id: data?.user_id!,
            full_name: data?.full_name!,
            firstname: data?.firstname!,
            lastname: data?.lastname!,
            email: data?.email!,
            username: data?.username!,
            phone: data?.phone_number!,
        })
        if (data?.img) {
            const url = "data:image/*;base64," + data?.img;
            setProfileImg(url)
        }
    }

    const onImagePickHandler = (e: React.FormEvent<HTMLInputElement>): void => {
        if (e.currentTarget.files?.item(0)) {
            const file = e.currentTarget.files.item(0);
            const reader = new FileReader();
            reader.readAsDataURL(file!);
            reader.onload = async () => {
                setProfileImg(reader.result as string);
                WebAPI.userService.uploadImg(file!);
            }
        }
    }

    return (
        <IonPage className="home">
            <IonHeader>
                <IonToolbar>
                    <IonButtons>
                        <IonMenuButton/>
                        <IonTitle class="ion-text-center">Személyes Profil</IonTitle>
                    </IonButtons>
                </IonToolbar>
            </IonHeader>

            <IonLoading
                cssClass='loading'
                isOpen={isLoading}
                onDidDismiss={() => setLoading(false)}
                message={'Kérjük várjon...'}
                duration={5000}
            />

            <IonContent>
                <div className="topHeader"/>
                <IonGrid>
                    <IonRow>
                        <IonCol>
                            <IonAlert
                                isOpen={qrCode}
                                onDidDismiss={() => setQrCode(false)}
                                cssClass="my-custom-class"
                                header={"Warning!"}
                                message={"Ez egy jövőbeli funkció!"}
                                buttons={["OK"]}
                            />
                        </IonCol>
                    </IonRow>

                    <IonRow className="ion-justify-content-center">
                        <IonCol size="12" className="ion-justify-content-center ion-align-items-center ion-text-center">
                            <IonCard className="profileHeader">
                                <IonCardContent>
                                    <IonRow>
                                        <IonCol size="4">
                                            <img src={profileImg} alt="avatar" className="avatar"/>
                                        </IonCol>

                                        <IonCol size="8">

                                            <IonRow className="profileInfo">
                                                <IonCol size="12">
                                                    <IonText color="medium">
                                                        <p>{actualUser?.full_name} </p>
                                                        <p>
                                                            <i>{actualUser?.username}</i>
                                                        </p>
                                                    </IonText>
                                                </IonCol>
                                            </IonRow>

                                            <IonRow className="profileInfo">
                                                <IonCol size="12">
                                                    <IonText color="medium">
                                                        <div>születési idő:</div>
                                                        <div>
                                                            {actualUser?.birth_date}
                                                        </div>
                                                    </IonText>
                                                </IonCol>
                                            </IonRow>
                                        </IonCol>
                                    </IonRow>
                                    <IonRow>
                                        <IonCol size="4">
                                            <ImageSelectorComponent
                                                onClick={(e: React.FormEvent<HTMLInputElement>) => onImagePickHandler(e)}/>
                                        </IonCol>
                                    </IonRow>
                                </IonCardContent>
                            </IonCard>
                        </IonCol>
                    </IonRow>

                    <IonRow className="profileStatusContainer">
                        <IonCol size="12">
                            <IonCard className="profileCard">
                                <IonCardHeader>
                                    <IonRow className="profileStatus">
                                        <IonIcon icon={mailOutline}/>
                                        <IonCardSubtitle>{actualUser?.email}</IonCardSubtitle>
                                    </IonRow>
                                    <IonRow className="profileStatus">
                                        <IonIcon icon={callOutline}/>
                                        <IonCardSubtitle>{actualUser?.phone_number ?? "-"}</IonCardSubtitle>
                                    </IonRow>
                                    <IonRow className="profileStatus">
                                        <IonIcon icon={businessOutline}/>
                                        <IonCardSubtitle>
                                            {
                                                actualUser?.postcode + " " + actualUser?.city + " " + actualUser?.street + " " + actualUser?.street_number
                                            }
                                        </IonCardSubtitle>
                                    </IonRow>
                                </IonCardHeader>
                                <IonCardHeader>
                                    <IonRow/>
                                </IonCardHeader>
                                <IonCardContent>

                                    <IonRow>
                                        <IonCol>
                                            <IonButton fill="outline" routerLink={"/password/change"} expand="block">
                                                <p>Jelszó megváltoztatás</p>
                                            </IonButton>
                                        </IonCol>
                                        <IonCol>
                                            <IonButton fill="outline" expand="block" onClick={() => setQrCode(true)}>
                                                <p>QR CODE</p>
                                            </IonButton>
                                        </IonCol>
                                    </IonRow>

                                    <IonRow>
                                        <IonCol>
                                            <IonButton fill="outline" routerLink={"/modify"} expand="block">
                                                <p>Adatok módosítása</p>
                                            </IonButton>
                                        </IonCol>
                                    </IonRow>

                                </IonCardContent>
                            </IonCard>
                        </IonCol>
                    </IonRow>
                </IonGrid>
            </IonContent>
        </IonPage>
    );
};

export default ProfilePage;
