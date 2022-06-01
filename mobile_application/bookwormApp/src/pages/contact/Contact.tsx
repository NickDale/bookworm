import {
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
  IonMenuButton,
  IonPage,
  IonRow,
  IonText,
  IonTitle,
  IonToolbar
} from '@ionic/react';

import './Contact.scss';
import React from 'react';
import {
  chatboxEllipsesOutline,
  callOutline,
  mailOutline,
  statsChartOutline,
  logoJavascript,
  gitPullRequestOutline,
  logoAngular,
  bugOutline,
  logoPython,
  logoWindows,
  logoHtml5
} from 'ionicons/icons';
import ContactCellComponent from '../../components/ContactCell';
import { CONTACT_MAIL, CONTACT_PHONE } from '../../constans';

const ContactPage: React.FC = () => {

  return (
    <IonPage className="home">
      <IonHeader>
        <IonToolbar>
          <IonButtons>
            <IonMenuButton />
            <IonTitle class="ion-text-center">Lépjünk kapcsolatba</IonTitle>
          </IonButtons>
        </IonToolbar>
      </IonHeader>
      <IonContent>

        <div className="topHeader"/>
        <IonGrid>
          <IonRow className="ion-justify-content-center">
            <IonCol size="12" className="ion-justify-content-center ion-align-items-center ion-text-center">
              <IonCard className="profileHeader">
                <IonCardContent>
                  <IonRow>
                    <IonCol size="4">
                      <img src="/assets/me.jpg" alt="avatar" className="avatar" />
                    </IonCol>
                    <IonCol size="8">
                      <IonRow className="profileInfo">
                        <IonCol size="12">
                          <IonText color="dark" className="profileName">
                            <p>Balogh Norbert</p>
                          </IonText>
                          <IonText color="medium">
                            <p>Software engineer</p>
                          </IonText>
                        </IonCol>
                      </IonRow>
                    </IonCol>
                  </IonRow>
                  <IonRow>
                    <IonCol size="6">
                      <IonButton fill="outline" expand="block">
                        <IonIcon icon={mailOutline} size="small" />&nbsp;
                        <a href={"mailto:" + CONTACT_MAIL}>Üzenet</a>
                      </IonButton>
                    </IonCol>
                    <IonCol size="6">
                      <IonButton color="primary" expand="block">
                        <IonIcon icon={callOutline} size="small" />&nbsp;
                        <a href={"tel:" + CONTACT_PHONE}> Hívás</a>
                      </IonButton>
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
                    <IonIcon icon={chatboxEllipsesOutline} />
                    <IonCardSubtitle/>
                  </IonRow>
                </IonCardHeader>
                <IonCardContent>
                  <IonText>
                    <p>Bármi kérdése van keressen nyugodtan e-mailben vagy telefonon!</p>
                  </IonText>
                </IonCardContent>
              </IonCard>
            </IonCol>
            <IonCol size="12">
              <IonCard className="profileCard">
                <IonCardHeader>
                  <IonRow className="profileStatus">
                    <IonIcon icon={statsChartOutline} />
                    <IonCardSubtitle>TOP SKILLEK</IonCardSubtitle>
                  </IonRow>
                </IonCardHeader>
                <IonCardContent>
                  <IonRow>
                    <ContactCellComponent name={gitPullRequestOutline} />
                    <ContactCellComponent name={logoJavascript} />
                    <ContactCellComponent name={logoAngular} />
                    <ContactCellComponent name={logoPython} />
                    <ContactCellComponent name={logoWindows} />
                    <ContactCellComponent name={logoHtml5} />
                    <ContactCellComponent name={bugOutline} />
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

export default ContactPage;
