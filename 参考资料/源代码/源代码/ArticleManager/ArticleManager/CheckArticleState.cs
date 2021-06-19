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
    public partial class CheckArticleState : Form
    {
        SqlConnection Sc;
        SqlDataAdapter Sda;
        DataSet Ds;
        DataTable Dt;
        public CheckArticleState()
        {
            InitializeComponent();
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            Sc = new SqlConnection(Con);
            Sda = new SqlDataAdapter("select ActiveArticle.articleid,ActiveArticle.title,Author.name," +
                "(case when (select count(*) from ReviewerRelationship where ReviewerRelationship.articleid=ActiveArticle.articleid and canceled=0)=0 then '被收到但是还没有采取进一步行动' " +
                " when (select count(*) from ReviewerRelationship where ReviewerRelationship.articleid=ActiveArticle.articleid and canceled=0)>0 and (select count(*) from ReviewerRelationship where ReviewerRelationship.articleid=ActiveArticle.articleid and ReviewerRelationship.review is null and canceled=0)>0 then '审稿人已经被指派但是并非所有的评论已被回复' " +
                " when (select count(*) from ReviewerRelationship where ReviewerRelationship.articleid=ActiveArticle.articleid and canceled=0)>0 and (select count(*) from ReviewerRelationship where ReviewerRelationship.articleid=ActiveArticle.articleid and ReviewerRelationship.review is null and canceled=0)=0 and ActiveArticle.accepted=0 then '评论被回复但是还没有采取进一步行动' " +
                " when (select count(*) from ReviewerRelationship where ReviewerRelationship.articleid=ActiveArticle.articleid and canceled=0)>0 and (select count(*) from ReviewerRelationship where ReviewerRelationship.articleid=ActiveArticle.articleid and ReviewerRelationship.review is null and canceled=0)=0 and ActiveArticle.accepted=1 and copyright=0 then '编辑已发送回复但是没有采取进一步行动' " +
                " when (select count(*) from ReviewerRelationship where ReviewerRelationship.articleid=ActiveArticle.articleid and canceled=0)>0 and (select count(*) from ReviewerRelationship where ReviewerRelationship.articleid=ActiveArticle.articleid and ReviewerRelationship.review is null and canceled=0)=0 and ActiveArticle.accepted=1 and copyright=1 and published=0 then '文章已经被收到并且版权表格已经发出' " +
                "end)" +
                "from ActiveArticle,Author,AuthorRelationship where ActiveArticle.articleid=AuthorRelationship.articleid and AuthorRelationship.authorid=Author.authorid", Sc);
            Ds = new DataSet();
            Sda.Fill(Ds);
            Dt = Ds.Tables[0];
            dataGridView1.AutoGenerateColumns = true;
            dataGridView1.DataSource = Dt;
            dataGridView1.AllowUserToAddRows = false;
            dataGridView1.ReadOnly = true;
            dataGridView1.Columns[0].HeaderCell.Value = "ID";
            dataGridView1.Columns[1].HeaderCell.Value = "标题";
            dataGridView1.Columns[2].HeaderCell.Value = "作者";
            dataGridView1.Columns[3].HeaderCell.Value = "文章状态";
            dataGridView1.Columns[0].Width = 40;
            dataGridView1.Columns[1].Width = 150;
            dataGridView1.Columns[2].Width = 75;
            dataGridView1.Columns[3].Width = 244;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            if (dataGridView1.Rows.Count == 0)
            {
                MessageBox.Show("未选中目标");
                return;
            }
            button2.Enabled = false;
            CheckReview cr = new CheckReview(Convert.ToInt32(dataGridView1.CurrentRow.Cells[0].Value));
            cr.ShowDialog();
            button2.Enabled = true;
        }

        private void CheckArticleState_Load(object sender, EventArgs e)
        {
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
        }
    }
}
