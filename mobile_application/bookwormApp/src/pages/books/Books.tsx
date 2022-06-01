import {
  IonButtons,
  IonContent,
  IonHeader,
  IonInfiniteScroll,
  IonInfiniteScrollContent,
  IonList,
  IonLoading,
  IonMenuButton,
  IonPage,
  IonTitle,
  IonToolbar
} from '@ionic/react';
import React, {
  useEffect,
  useState
} from "react";
import './Books.css';
import { Book } from '../../client/book';
import BookListComponent from '../../components/BookListComponent';
import { WebAPI } from '../../services/webAPI';

const BookPage: React.FC = () => {
  const [books, setBooks] = useState<Book[]>([]);
  const [page, setPage] = useState<number>(0);
  const size = 10;
  const [disableInfiniteScroll, setDisableInfiniteScroll] = useState<boolean>(false);
  const [isLoading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    setTimeout(() => {
      fetchData();
      setLoading(false);
    }, 1000)
  }, []);

  const fetchData = async (): Promise<void> => {

    const data: Book[] = await WebAPI.bookService.listBooks(page,size);
    if (data && data.length > 0) {
      setBooks([...books, ...data]);
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

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonButtons slot="start">
            <IonMenuButton />
          </IonButtons>
          <IonTitle>Könyveink</IonTitle>
        </IonToolbar>
      </IonHeader>

      <IonContent fullscreen>
        <IonList>
          <IonLoading
            cssClass='loading'
            isOpen={isLoading}
            onDidDismiss={() => setLoading(false)}
            message={'Kérjük várjon...'}
            duration={5000}
          />
          {
            books.map((movie, index) => <BookListComponent item={movie} key={index} />)
          }
        </IonList>
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

export default BookPage;
