using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ArticleManager
{
    public partial class CheckReview : Form
    {
        int articleid;
        public CheckReview(int x)
        {
            articleid = x;
            InitializeComponent();
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            SqlConnection Sc = new SqlConnection(Con);
            SqlDataAdapter Sda = new SqlDataAdapter("select ReviewerRelationship.createtime,ReviewerRelationship.datesent,Reviewer.name,ReviewerRelationship.review from Reviewer,ReviewerRelationship where Reviewer.reviewerid=ReviewerRelationship.reviewerid and ReviewerRelationship.articleid=" + articleid + " and canceled=0 and datesent is not null", Sc);
            DataSet Ds = new DataSet();
            Sda.Fill(Ds);
            DataTable Dt = Ds.Tables[0];
            dataGridView1.AutoGenerateColumns = true;
            dataGridView1.DataSource = Dt;
            dataGridView1.AllowUserToAddRows = false;
            dataGridView1.ReadOnly = true;
            dataGridView1.Columns[0].HeaderCell.Value = "分配时间";
            dataGridView1.Columns[1].HeaderCell.Value = "评论时间";
            dataGridView1.Columns[2].HeaderCell.Value = "审稿人";
            dataGridView1.Columns[3].HeaderCell.Value = "评论内容";
            dataGridView1.Columns[0].Width = 80;
            dataGridView1.Columns[1].Width = 80;
            dataGridView1.Columns[2].Width = 75;
            dataGridView1.Columns[3].Width = 300;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void CheckReview_Load(object sender, EventArgs e)
        {
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
        }
    }
}
