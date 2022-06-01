import {
  IonButtons,
  IonContent,
  IonHeader,
  IonInfiniteScroll,
  IonInfiniteScrollContent,
  IonList,
  IonListHeader,
  IonLoading,
  IonMenuButton,
  IonPage,
  IonTitle,
  IonToolbar
} from '@ionic/react';
import { useLocation } from 'react-router';
import React, {
  useEffect,
  useState
} from "react";
import './Loans.css';
import { LoanItem } from '../../client/loan';
import LoanComponent from '../../components/LoanListComponent';
import { WebAPI } from '../../services/webAPI';

const LoanPage: React.FC = () => {

  const location = useLocation();
  const [loans, setLoans] = useState<LoanItem[]>([]);
  const [page, setPage] = useState<number>(0);
  const size = 10;
  const [disableInfiniteScroll, setDisableInfiniteScroll] = useState<boolean>(false);
  const [isLoading, setLoading] = useState<boolean>(true);
  const [onlyActive] = useState<boolean>(location.pathname !== '/history');

  useEffect(() => {
    fetchData();
    setLoading(false);
  }, []);

  const fetchData = async (): Promise<void> => {
    const data: LoanItem[] = await WebAPI.loanService.getLoanItemsByUserId(1, page, size, onlyActive);
    if (data && data.length > 0) {
      setLoans([...loans, ...data]);
      if (data.length < size) {
        setDisableInfiniteScroll(true);
      }
      else {
        setPage(page + 1);
      }
    }
    else {
      setDisableInfiniteScroll(true);
    }
  }

  const searchNext = async (e: CustomEvent<void>): Promise<void> => {
    await fetchData();
    (e.target as HTMLIonInfiniteScrollElement).complete();
  }

  const loanItems = (): JSX.Element =>
    <IonList>
      {
        loans.length > 0
          ? loans.map((movie, index) => <LoanComponent item={movie} key={index} />)
          : "A rendszer szerint Önnél nincs egyetlen aktív kölcsönzés sem."
      }
    </IonList>

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonButtons slot="start">
            <IonMenuButton />
          </IonButtons>
          <IonTitle>{onlyActive ? 'Aktuális kölcsönzések' : 'Korábbi kölcsönzések'}</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent fullscreen>
        <IonListHeader/>
        <IonLoading
          cssClass='loading'
          isOpen={isLoading}
          onDidDismiss={() => setLoading(false)}
          message={'Kérjük várjon...'}
          duration={10000}
        />
        {
          loanItems()
        }
        <IonInfiniteScroll threshold="50px"
          disabled={disableInfiniteScroll}
          onIonInfinite={(e: CustomEvent<void>) => searchNext(e)}>
          <IonInfiniteScrollContent loadingText="Betöltése ..." loadingSpinner="bubbles">
          </IonInfiniteScrollContent>
        </IonInfiniteScroll>

      </IonContent>
    </IonPage>
  );
};

export default LoanPage;
