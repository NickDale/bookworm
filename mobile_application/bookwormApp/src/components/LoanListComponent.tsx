import React, {useEffect, useState} from "react";
import {IonAlert, IonButton, IonCard, IonCardContent, IonCol, IonItem, IonRow, IonText,} from "@ionic/react";
import {LoanItem} from "../client/loan";
import './LoanListComponent.css';
import {BarcodeImg} from "../client/book";
import {WebAPI} from "../services/webAPI";
import {EXPIRE_BEFORE_DAY} from "../constans";

interface IProps {
    item: LoanItem;
}

const LoanListComponent: React.FC<IProps> = (props: IProps): JSX.Element => {
    const [showBarcode, setShowBarcode] = useState<boolean>(false);
    const [showResponseMsg, setShowResponseMsg] = useState<boolean>(false);
    const [message, setMessage] = useState<string>("");
    const [barcodeImgSrc, setBarcodeImgSrc] = useState<string>();

    const estimateEndDate = new Date(props.item.estimate_end_date);
    const today = new Date();
    const near = estimateEndDate.getTime() <= new Date(new Date().setDate(today.getDate() + EXPIRE_BEFORE_DAY)).getTime();
    const expired = estimateEndDate.getTime() <= today.getTime();

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async (): Promise<void> => {
        const img: BarcodeImg = await WebAPI.bookService.getBarcodeImageByBookId(props.item.book.book_id);
        const url = "data:image/png;base64," + img.base64img!;
        setBarcodeImgSrc(url);
    }

    const needMoreTime = async (): Promise<void> => {
        const result: string = await WebAPI.loanService.extendTheLoanByLoanId(props.item.loan_id);
        setMessage(result);
        setShowResponseMsg(true);
    }

    return (
        <IonItem>
            <IonCard>
                <IonCardContent>
                    <IonRow>
                        <IonCol>
                            <IonAlert
                                isOpen={showBarcode}
                                onDidDismiss={() => setShowBarcode(false)}
                                cssClass="barcode_wrapper"
                                message={`<img className={barcode} src=${barcodeImgSrc} alt="barcode"/>`}
                            />
                        </IonCol>
                    </IonRow>
                    <IonRow>
                        <IonCol>
                            <IonAlert
                                isOpen={showResponseMsg}
                                onDidDismiss={() => setShowResponseMsg(false)}
                                cssClass="my-custom-class"
                                header={"INFO!"}
                                message={message}
                                buttons={["OK"]}
                            />
                        </IonCol>
                    </IonRow>

                    <IonRow>
                        <IonCol size="4">
                            <img src={props.item.book.img_link} alt={props.item.book.title} className="avatar"/>
                        </IonCol>
                        <IonCol size="8">
                            <IonRow>
                                <IonCol size="12">
                                    <IonText>
                                        <h1>{props.item.book.title}</h1>
                                    </IonText>
                                    <IonText>
                                        <h2>
                                            {props.item.book.authors_string}
                                        </h2>
                                    </IonText>
                                </IonCol>
                            </IonRow>
                            <IonRow>
                                <IonCol size="6">
                                    <IonText>
                                        <h2>Kölcsönzés:</h2>
                                        <h2>
                                            {props.item.creation_date}
                                        </h2>
                                    </IonText>
                                </IonCol>
                                <IonCol size="6">
                                    <IonText>
                                        <h2>Lejárat {props.item.extended_end_date ? "( hosszabbított ) " : ""}:</h2>
                                        <h2 className={expired ? 'expired' : (near ? 'near' : '')}>
                                            <strong> {props.item.estimate_end_date}</strong>
                                        </h2>
                                    </IonText>
                                </IonCol>
                            </IonRow>

                            <IonRow>
                                <IonCol/>
                            </IonRow>
                            <IonRow>
                                <IonCol>
                                    <IonButton fill="outline" expand="block"
                                               disabled={!!props.item.extended_end_date}
                                               onClick={() => needMoreTime()}>
                                        <p>Hosszabbítás</p>
                                    </IonButton>
                                </IonCol>
                            </IonRow>
                            <IonRow>
                                <IonCol/>
                            </IonRow>
                            <IonRow>
                                <IonCol>
                                    <IonButton fill="outline" expand="block" onClick={() => setShowBarcode(true)}>
                                        <p>Vonalkód megjelenítése</p>
                                    </IonButton>
                                </IonCol>
                            </IonRow>
                        </IonCol>
                        <IonCol>
                            <IonRow>
                                <IonCol>
                                    <IonText>
                                        <h3>Rövid leírás: </h3>
                                        <h3>
                                            {
                                                props.item.book.short_description
                                            }
                                        </h3>
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

export default LoanListComponent;