using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

#nullable disable

namespace BookWorm.Models
{
    [Index(nameof(book_id), Name = "book_id")]
    [Index(nameof(user_id), Name = "user_id_fk")]
    public partial class book_loan
    {
        public book_loan()
        {
            reminder = new HashSet<reminder>();
        }

        [Key]
        [Column(TypeName = "int(11)")]
        public int id { get; set; }
        [Column(TypeName = "date")]
        public DateTime estimate_end_date { get; set; }
        [Column(TypeName = "date")]
        public DateTime? end_date { get; set; }
        [Column(TypeName = "int(11)")]
        public int book_id { get; set; }
        [Column(TypeName = "datetime")]
        public DateTime created_date { get; set; }
        [Required]
        [StringLength(100)]
        public string created_by { get; set; }
        [StringLength(100)]
        public string last_mod_user { get; set; }
        [Column(TypeName = "datetime")]
        public DateTime? last_mod_date { get; set; }
        [Column(TypeName = "date")]
        public DateTime? extended_date { get; set; }
        [Column(TypeName = "int(11)")]
        public int user_id { get; set; }

        [ForeignKey(nameof(book_id))]
        [InverseProperty("book_loan")]
        public virtual book book { get; set; }
        [ForeignKey(nameof(user_id))]
        [InverseProperty(nameof(users.book_loan))]
        public virtual users user { get; set; }
        [InverseProperty("loan_item")]
        public virtual ICollection<reminder> reminder { get; set; }
    }
}
