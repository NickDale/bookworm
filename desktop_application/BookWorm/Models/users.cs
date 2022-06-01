using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using Microsoft.EntityFrameworkCore;

#nullable disable

namespace BookWorm.Models
{
    [Index(nameof(active), Name = "active")]
    [Index(nameof(email), Name = "email", IsUnique = true)]
    [Index(nameof(QR_code), Name = "userQRIndex")]
    [Index(nameof(user_type_id), Name = "user_type_id")]
    [Index(nameof(username), Name = "username", IsUnique = true)]
    public partial class users
    {
        public users()
        {
            book_loan = new HashSet<book_loan>();
        }

        [Key]
        [Column(TypeName = "int(11)")]
        public int id { get; set; }
        [StringLength(10)]
        public string title { get; set; }
        [Required]
        [StringLength(100)]
        public string firstname { get; set; }
        [StringLength(100)]
        public string middlename { get; set; }
        [Required]
        [StringLength(100)]
        public string lastname { get; set; }
        [Required]
        [StringLength(100)]
        public string email { get; set; }
        [Required]
        [StringLength(15)]
        public string username { get; set; }
        [StringLength(500)]
        public string encrypted_password { get; set; }
        [StringLength(500)]
        public string salt { get; set; }
        public bool active { get; set; }
        [Column(TypeName = "int(11)")]
        public int user_type_id { get; set; }
        [StringLength(100)]
        public string QR_code { get; set; }
        [Column(TypeName = "datetime")]
        public DateTime created_date { get; set; }
        [Required]
        [StringLength(100)]
        public string created_by { get; set; }
        [StringLength(100)]
        public string last_mod_user { get; set; }
        [Column(TypeName = "datetime")]
        public DateTime? last_mod_date { get; set; }
        [Column(TypeName = "datetime")]
        public DateTime? deactivation_date { get; set; }
        [StringLength(15)]
        public string postcode { get; set; }
        [StringLength(30)]
        public string city { get; set; }
        [StringLength(30)]
        public string street { get; set; }
        [StringLength(10)]
        public string street_number { get; set; }
        [Column(TypeName = "datetime")]
        public DateTime? birth_date { get; set; }
        [StringLength(2)]
        public string language { get; set; }
        [StringLength(15)]
        public string phone_number { get; set; }

        [ForeignKey(nameof(user_type_id))]
        [InverseProperty("users")]
        public virtual user_type user_type { get; set; }
        [InverseProperty("user")]
        public virtual ICollection<book_loan> book_loan { get; set; }
    }
}
