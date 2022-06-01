import React, {
    useState,
    useEffect
} from "react";
import {
    IonPage,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonContent,
    IonLoading
} from "@ionic/react";

import {
    withRouter, RouteComponentProps
} from "react-router-dom";
import BookDetailPageComponent from '../../components/BookDetailComponent';
import { BarcodeImg, Book } from '../../client/book';
import { WebAPI } from "../../services/webAPI";
import { BASE64_IMG } from "../../constans";

interface IProps extends RouteComponentProps<{ id: string }> {
}

const BookDetailPage: React.FC<IProps> = (props: IProps): JSX.Element => {
    const [book, setBook] = useState<Book>();
    const [barcodeImgSrc, setBarcodeImgSrc] = useState<string>();
    const [isLoading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        setTimeout(() => {
            fetchData();
            setLoading(false);
        }, 1000)
    }, []);

    const fetchData = async (): Promise<void> => {
        const data: Book = await WebAPI.bookService.getBookById(props.match.params.id);
        setBook(data);

        console.log("most call: " + props.match.params.id);
        const img: BarcodeImg = await WebAPI.bookService.getBarcodeImageByBookId(props.match.params.id);
        console.log("img: " + img);
        setBarcodeImgSrc(BASE64_IMG + img.base64img!);
    }

    return (
        <IonPage>
            <IonHeader>
                <IonToolbar>
                    <IonTitle>{book?.title}</IonTitle>
                </IonToolbar>
            </IonHeader>
            <IonContent fullscreen>
                <IonLoading
                    cssClass='loading'
                    isOpen={isLoading}
                    onDidDismiss={() => setLoading(false)}
                    message={'Kérjük várjon...'}
                    duration={5000}
                />
                <BookDetailPageComponent imgSrc={barcodeImgSrc} book={book} />
            </IonContent>
        </IonPage>
    );
}
export default withRouter(BookDetailPage);