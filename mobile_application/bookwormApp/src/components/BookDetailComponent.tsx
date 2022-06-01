import React, {useState} from "react";
import {
    IonAlert,
    IonBackButton,
    IonButton,
    IonCard,
    IonCardContent,
    IonCardHeader,
    IonCardSubtitle,
    IonCardTitle,
    IonCol,
    IonRow,
    IonText
} from "@ionic/react";
import {Book} from "../client/book";
import './BookDetailComponent.css';

interface IProps {
    book?: Book;
    imgSrc?: string;
}

const BookDetailPageComponent: React.FC<IProps> = (props: IProps): JSX.Element => {
    const [barcode, setBarcode] = useState<boolean>(false);

    return (
        <div>
            <IonRow>
                <IonCol>
                    <IonAlert
                        isOpen={barcode}
                        onDidDismiss={() => setBarcode(false)}
                        cssClass="barcode_wrapper"
                        message={`<img className={barcode} src=${props.imgSrc}  alt="barcode" />`}
                    />
                </IonCol>
            </IonRow>
            <IonCard>
                <IonRow>
                    <IonCol size="2"/>
                    <IonCol size="8">
                        <IonText>
                            <img src={props.book?.img_link} alt={props.book?.title}/>
                        </IonText>
                    </IonCol>
                </IonRow>
                <IonCardHeader class="ion-text-center">
                    <IonCardSubtitle>
                        <p>{props.book?.authors_string}</p>
                    </IonCardSubtitle>
                    <IonCardTitle>{props.book?.title}</IonCardTitle>
                </IonCardHeader>
                <IonCard>
                    <IonCardContent>
                        <IonRow>
                            <IonCol size="6">
                                <IonText>
                                    <p>Kategória: <b>{props.book?.category.name} </b></p>
                                </IonText>
                            </IonCol>
                            <IonCol size="6">
                                <IonText>
                                    <p>ISBN szám: <b>{props.book?.isbn} </b></p>
                                </IonText>
                            </IonCol>
                        </IonRow>
                        <IonRow>
                            <IonCol size="6">
                                <IonText>
                                    <p>vonalkód: <b>{props.book?.barcode} </b></p>
                                </IonText>
                            </IonCol>
                        </IonRow>
                        <IonRow>
                            <IonCol size="3"/>
                            <IonCol size="6">
                                <IonButton fill="outline" expand="block" onClick={() => setBarcode(true)}>
                                    <p>Vonalkód megjelenítése</p>
                                </IonButton>
                            </IonCol>
                        </IonRow>
                    </IonCardContent>
                </IonCard>
                <IonCard>
                    <IonCardContent>
                        <p>Rövid leírás:</p>
                        <p>{props.book?.short_description}</p>
                    </IonCardContent>
                </IonCard>
            </IonCard>
            <IonBackButton defaultHref="/"/>
        </div>
    );
}

export default BookDetailPageComponent;