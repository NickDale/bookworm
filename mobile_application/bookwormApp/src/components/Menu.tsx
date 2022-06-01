import {
    IonContent,
    IonIcon,
    IonItem,
    IonLabel,
    IonList,
    IonListHeader,
    IonMenu,
    IonMenuToggle,
    IonNote,
    NavContext,
} from '@ionic/react';

import {useLocation} from 'react-router-dom';
import {logOutOutline,} from 'ionicons/icons';
import './Menu.css';
import React, {useCallback, useContext} from 'react';
import {appPages} from '../menu';
import {WebAPI} from '../services/webAPI';
import {AuthContext} from "../context";

const Menu: React.FC = () => {
    const location = useLocation();
    const loggedUser = WebAPI.getLoggedUser();
    const context = useContext(AuthContext);

    const {navigate} = useContext(NavContext);
    const logOut = useCallback(() => navigate("/", "back", "replace"), [navigate]);

    const doLogout = () => {
        WebAPI.logout();
        logOut();
    }

    return (
        context.isAuthenticated ?
            <IonMenu contentId="main" type="overlay">
                <IonContent>
                    <IonList id="inbox-list">
                        <IonListHeader>{loggedUser?.full_name}</IonListHeader>
                        <IonNote>{loggedUser?.email}</IonNote>
                        {
                            appPages.map((appPage, index) => {
                                return (
                                    <IonMenuToggle key={index} autoHide={false}>
                                        <IonItem className={location.pathname === appPage.url ? 'selected' : ''}
                                                 routerLink={appPage.url} routerDirection="none" lines="none"
                                                 detail={false}>
                                            <IonIcon slot="start" ios={appPage.iosIcon} md={appPage.mdIcon}/>
                                            <IonLabel>{appPage.title}</IonLabel>
                                        </IonItem>
                                    </IonMenuToggle>
                                );
                            })
                        }
                    </IonList>

                    <IonList id="labels-list">
                        <IonItem className="logout" lines="none" onClick={() => doLogout()}>
                            <IonIcon slot="start" icon={logOutOutline}/>
                            <IonLabel>Kijelentkezés</IonLabel>
                        </IonItem>
                    </IonList>
                </IonContent>
            </IonMenu>
            : <></>
    );
};

export default Menu;
