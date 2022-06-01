import React from "react";
import {IonCard, IonCardContent, IonCol, IonItem, IonRow, IonText} from "@ionic/react";
import {Book} from "../client/book";

interface IProps {
    item: Book;
}

const BookListComponent: React.FC<IProps> = (props: IProps): JSX.Element => {
    return (
        <IonItem routerLink={`/book/detail/${props.item.book_id}`}>
            <IonCard>
                <IonCardContent>
                    <IonRow>
                        <IonCol size="4">
                            <img src={props.item.img_link} alt={props.item.title} className="avatar"/>
                        </IonCol>
                        <IonCol size="8">
                            <IonRow>
                                <IonCol size="12">
                                    <IonText>
                                        <h1>{props.item.title}</h1>
                                    </IonText>
                                </IonCol>
                            </IonRow>
                            <IonRow>
                                <IonCol size="12">
                                    <IonText>
                                        <h3>Szerző:</h3>
                                        <h2>
                                            {props.item.authors_string}
                                        </h2>
                                    </IonText>
                                </IonCol>
                            </IonRow>
                            <IonRow>
                                <IonCol size="12">
                                    <IonText>
                                        <h3>Rövid leírás: </h3>
                                        <h2>
                                            {
                                                props.item.short_description.substring(0, 100) + "..."
                                            }
                                        </h2>
                                    </IonText>
                                </IonCol>
                            </IonRow>
                        </IonCol>
                    </IonRow>
                </IonCardContent>
            </IonCard>
        </IonItem>
    );
}

export default BookListComponent;