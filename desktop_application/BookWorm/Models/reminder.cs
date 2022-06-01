using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

#nullable disable

namespace BookWorm.Models
{
    [Index(nameof(loan_item_id), Name = "loan_item_id")]
    public partial class reminder
    {
        [Key]
        [Column(TypeName = "int(11)")]
        public int id { get; set; }
        [Column(TypeName = "int(11)")]
        public int loan_item_id { get; set; }
        [Column(TypeName = "date")]
        public DateTime notification_date { get; set; }

        [ForeignKey(nameof(loan_item_id))]
        [InverseProperty(nameof(book_loan.reminder))]
        public virtual book_loan loan_item { get; set; }
    }
}
